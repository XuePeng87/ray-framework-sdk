package cc.xuepeng.ray.framework.sdk.mgt.exception;

import cc.xuepeng.ray.framework.core.util.exception.BaseException;

/**
 * 用户认证失败的异常类。
 *
 * @author xuepeng
 */
public class SysUserAuthenticationException extends BaseException {

    /**
     * 构造函数。
     */
    public SysUserAuthenticationException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysUserAuthenticationException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysUserAuthenticationException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysUserAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
