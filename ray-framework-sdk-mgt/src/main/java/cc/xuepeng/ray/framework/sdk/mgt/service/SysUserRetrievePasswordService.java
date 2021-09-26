package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysUserRetrievePassword;

/**
 * 用户找回密码服务接口。
 *
 * @author xuepeng
 */
public interface SysUserRetrievePasswordService {

    /**
     * 找回密码时验证邮箱或手机号是否存在。
     *
     * @param userRetrievePassword 修改密码实体类。
     * @return 是否存在。
     */
    boolean checkInfomation(final SysUserRetrievePassword userRetrievePassword);

    /**
     * 发送找回密码所需的验证码。
     *
     * @param userRetrievePassword 用户找回密码的实体类。
     */
    void sendVerificationCode(final SysUserRetrievePassword userRetrievePassword);

    /**
     * 重置密码。
     *
     * @param userRetrievePassword 用户找回密码的实体类。
     * @return 是否重置成功。
     */
    boolean resetPassword(final SysUserRetrievePassword userRetrievePassword);

    /**
     * 判断是否到达发送验证码的次数的上限。
     *
     * @param userRetrievePassword 用户找回密码的实体类。
     * @return 找回密码时，发送验证码的次数是否过多。
     */
    boolean isUpperLimit(final SysUserRetrievePassword userRetrievePassword);

}
