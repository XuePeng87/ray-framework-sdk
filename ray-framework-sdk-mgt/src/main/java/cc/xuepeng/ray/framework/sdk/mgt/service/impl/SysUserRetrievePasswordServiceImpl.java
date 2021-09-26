package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.core.util.date.DateTimeUtil;
import cc.xuepeng.ray.framework.sdk.mgt.config.MgtConfiguration;
import cc.xuepeng.ray.framework.sdk.mgt.entity.SysUserRetrievePassword;
import cc.xuepeng.ray.framework.sdk.mgt.enums.ForgetPasswordType;
import cc.xuepeng.ray.framework.sdk.mgt.exception.PinCodeErrorException;
import cc.xuepeng.ray.framework.sdk.mgt.exception.PinCodeIntervalException;
import cc.xuepeng.ray.framework.sdk.mgt.exception.PinCodeTimeOutException;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysUserRetrievePasswordService;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysUserService;
import cc.xuepeng.ray.framework.sdk.mgt.service.impl.sender.SendStrategy;
import cc.xuepeng.ray.framework.sdk.mgt.service.impl.sender.SendStrategyFactory;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 用户找回密码服务实现类。
 *
 * @author xuepeng
 */
@Service("userRetrievePasswordService")
public class SysUserRetrievePasswordServiceImpl implements SysUserRetrievePasswordService {

    /**
     * 找回密码时验证邮箱或手机号是否存在。
     *
     * @param userRetrievePassword 修改密码实体类。
     * @return 是否存在。
     */
    @Override
    public boolean checkInfomation(final SysUserRetrievePassword userRetrievePassword) {
        long count = 0;
        if (ForgetPasswordType.EMAIL.equals(userRetrievePassword.getType())) {
            count = sysUserService.countByEmail(userRetrievePassword.getContact());
        }
        if (ForgetPasswordType.PHONE.equals(userRetrievePassword.getType())) {
            count = sysUserService.countByPhone(userRetrievePassword.getContact());
        }
        return count > 0;
    }

    /**
     * 发送找回密码所需的验证码。
     *
     * @param userRetrievePassword 用户找回密码的实体类。
     */
    @Override
    public void sendVerificationCode(final SysUserRetrievePassword userRetrievePassword) {
        // 验证重置密码的信息是否正确
        final Long userId = findUserId(userRetrievePassword);
        // 验证用户发送验证码是否超过60秒
        if (verifySendInterval(userId)) {
            throw new PinCodeIntervalException("每分钟只可发送一次验证码。");
        }
        // 发送验证码
        final SendStrategy sendStrategy = sendStrategyFactory.getInstance(userRetrievePassword.getType());
        final String code = sendStrategy.send(userRetrievePassword);
        // 发送完成验证码的业务
        afterSendPinCode(userId, userRetrievePassword.getType(), code);
    }

    /**
     * 重置密码。
     *
     * @param userRetrievePassword 用户找回密码的实体类。
     * @return 是否重置成功。
     */
    @Override
    public boolean resetPassword(final SysUserRetrievePassword userRetrievePassword) {
        // 判断验证码是否合法。
        final Long userId = findUserId(userRetrievePassword);
        final String code = getPinCode(userId);
        if (StringUtils.isEmpty(code)) {
            throw new PinCodeTimeOutException("验证码过期。");
        }
        if (!code.equalsIgnoreCase(userRetrievePassword.getCode())) {
            throw new PinCodeErrorException("验证码不正确。");
        }
        // 更新密码
        if (sysUserService.updateSecret(userId, userRetrievePassword.getPassword())) {
            afterResetPassword(userId, userRetrievePassword.getType());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 判断是否到达发送验证码的次数的上限。
     *
     * @param userRetrievePassword 用户找回密码的实体类。
     * @return 找回密码时，发送验证码的次数是否过多。
     */
    @Override
    public boolean isUpperLimit(final SysUserRetrievePassword userRetrievePassword) {
        // 验证重置密码的信息是否正确
        final Long userId = findUserId(userRetrievePassword);
        final String key = createSendUpperLimitKey(userId, userRetrievePassword.getType());
        final String count = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotEmpty(count) &&
                Integer.parseInt(count) >= mgtConfiguration.getMgtCacheConfiguration().getSendUpperLimitCount()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 找回密码时，验证用户信息是否正确。
     *
     * @param userRetrievePassword 用户找回密码的实体类。
     * @return 信息是否正确。
     */
    private Long findUserId(final SysUserRetrievePassword userRetrievePassword) {
        Long userId = 0L;
        if (ForgetPasswordType.EMAIL.equals(userRetrievePassword.getType())) {
            userId = sysUserService.findIdByEmail(userRetrievePassword.getContact());
        }
        if (ForgetPasswordType.PHONE.equals(userRetrievePassword.getType())) {
            userId = sysUserService.findIdByPhone(userRetrievePassword.getContact());
        }
        return userId;
    }

    /**
     * 验证发送的时间间隔是否还未结束。
     *
     * @param userId 用户主键。
     * @return 发送的时间间隔是否还未结束。
     */
    private boolean verifySendInterval(final Long userId) {
        // 保存发送时间间隔
        final Boolean hasKey = stringRedisTemplate.hasKey(
                mgtConfiguration
                        .getMgtCacheConfiguration()
                        .getSendIntervalKey() + userId
        );
        return BooleanUtils.isTrue(hasKey);
    }

    /**
     * 发送验证码后的业务。
     *
     * @param userId             用户主键。
     * @param forgetPasswordType 发送类型。
     * @param code               验证码。
     */
    private void afterSendPinCode(final Long userId,
                                  final ForgetPasswordType forgetPasswordType,
                                  final String code) {
        stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.openPipeline();
            try {
                // 发送成功后，设置发送时间间隔
                connection.set(
                        (mgtConfiguration.getMgtCacheConfiguration().getSendIntervalKey() + userId).getBytes(),
                        StringUtils.EMPTY.getBytes(),
                        Expiration.from(mgtConfiguration.getMgtCacheConfiguration().getSendIntervalTime(), TimeUnit.SECONDS),
                        RedisStringCommands.SetOption.UPSERT
                );
                // 发送成功后，设置用户发送总次数
                final String sendUpperLimitKey = createSendUpperLimitKey(userId, forgetPasswordType);
                connection.incr(sendUpperLimitKey.getBytes());
                connection.expire(
                        sendUpperLimitKey.getBytes(),
                        DateTimeUtil.getSecoudFromNowToTomorrow()
                );
                // 设置用户的验证码内容
                connection.set(
                        (mgtConfiguration.getMgtCacheConfiguration().getPinCodeKey() + userId).getBytes(),
                        code.getBytes(),
                        Expiration.from(mgtConfiguration.getMgtCacheConfiguration().getPinCodeTime(), TimeUnit.MINUTES),
                        RedisStringCommands.SetOption.UPSERT
                );
            } finally {
                connection.closePipeline();
            }
            return null;
        });
    }

    /**
     * 获取用户的验证码。
     *
     * @param userId 用户主键。
     * @return 用户的验证码。
     */
    private String getPinCode(final Long userId) {
        return stringRedisTemplate.opsForValue().get(mgtConfiguration.getMgtCacheConfiguration().getPinCodeKey() + userId);
    }

    /**
     * 重置密码后的业务。
     *
     * @param userId             用户主键。
     * @param forgetPasswordType 发送类型。
     */
    private void afterResetPassword(final Long userId,
                                    final ForgetPasswordType forgetPasswordType) {
        stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.openPipeline();
            // 删除用户的验证码。
            connection.del((mgtConfiguration.getMgtCacheConfiguration().getPinCodeKey() + userId).getBytes());
            // 重置用户每次发送次数。
            connection.set(createSendUpperLimitKey(userId, forgetPasswordType).getBytes(), "0".getBytes());
            connection.closePipeline();
            return null;
        });
    }

    /**
     * 创建用户发送次数的Redis Key。
     *
     * @param userId             用户主键。
     * @param forgetPasswordType 忘记密码类型。
     * @return 用户发送次数的Redis Key。
     */
    private String createSendUpperLimitKey(final Long userId,
                                           final ForgetPasswordType forgetPasswordType) {
        return String.format(mgtConfiguration.getMgtCacheConfiguration().getSendUpperLimitKey(), forgetPasswordType.ordinal(), userId);
    }

    /**
     * 自动装配发送策略工厂。
     *
     * @param sendStrategyFactory 发送策略工厂。
     */
    @Autowired
    public void setSendStrategyFactory(SendStrategyFactory sendStrategyFactory) {
        this.sendStrategyFactory = sendStrategyFactory;
    }

    /**
     * 自动装配用户业务处理接口。
     *
     * @param sysUserService 用户业务处理接口。
     */
    @Autowired
    public void setSysUserService(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 自动装配用户配置信息。
     *
     * @param mgtConfiguration 用户配置信息。
     */
    @Autowired
    public void setMgtConfiguration(MgtConfiguration mgtConfiguration) {
        this.mgtConfiguration = mgtConfiguration;
    }

    /**
     * 自动装配Redis客户端。
     *
     * @param stringRedisTemplate Redis客户端。
     */
    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 发送策略工厂。
     */
    private SendStrategyFactory sendStrategyFactory;
    /**
     * 用户业务处理接口。
     */
    private SysUserService sysUserService;
    /**
     * 用户配置信息。
     */
    private MgtConfiguration mgtConfiguration;
    /**
     * Redis客户端。
     */
    private StringRedisTemplate stringRedisTemplate;

}
