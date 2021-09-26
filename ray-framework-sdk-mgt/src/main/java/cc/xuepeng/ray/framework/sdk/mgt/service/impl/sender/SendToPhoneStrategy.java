package cc.xuepeng.ray.framework.sdk.mgt.service.impl.sender;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysUserRetrievePassword;
import cc.xuepeng.ray.framework.sdk.mgt.enums.ForgetPasswordType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 发送验证码到手机。
 *
 * @author xuepeng
 */
@Slf4j
@Component
public class SendToPhoneStrategy implements SendStrategy {

    /**
     * 短信模板。
     */
    private static final String SMS_CONTENT = "亲爱的用户您好：\r\n 您正在申请管理系统找回密码服务，本次请求的邮件验证码是：{content} （为了保障验证码的时效性，请您15分钟内完成验证）。如非您本人操作，请忽略该邮件，您的原密码不会更改。";

    /**
     * @return 获取发送方式。
     */
    @Override
    public ForgetPasswordType getSendType() {
        return ForgetPasswordType.PHONE;
    }

    /**
     * 发送验证码。
     *
     * @param userRetrievePassword 找回密码的实体类。
     * @return 发送的验证码。
     */
    @Override
    public String send(final SysUserRetrievePassword userRetrievePassword) {
        throw new IllegalArgumentException("目前不支持短信发送验证码。");
    }

}
