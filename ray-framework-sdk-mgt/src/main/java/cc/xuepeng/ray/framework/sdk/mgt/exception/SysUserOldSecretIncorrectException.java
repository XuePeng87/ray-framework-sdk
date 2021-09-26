package cc.xuepeng.ray.framework.sdk.mgt.exception;

import cc.xuepeng.ray.framework.core.util.exception.BaseException;

/**
 * 修改密码时，旧密码不正确的异常类。
 *
 * @author xuepeng
 */
public class SysUserOldSecretIncorrectException extends BaseException {

    /**
     * 构造函数。
     */
    public SysUserOldSecretIncorrectException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysUserOldSecretIncorrectException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysUserOldSecretIncorrectException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysUserOldSecretIncorrectException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
