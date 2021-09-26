package cc.xuepeng.ray.framework.sdk.mgt.exception;

import cc.xuepeng.ray.framework.core.util.exception.BaseException;

/**
 * 验证码超时
 */
public class PinCodeTimeOutException extends BaseException {

    /**
     * 构造函数。
     */
    public PinCodeTimeOutException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public PinCodeTimeOutException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public PinCodeTimeOutException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public PinCodeTimeOutException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
