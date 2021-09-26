package cc.xuepeng.ray.framework.sdk.mgt.exception;

import cc.xuepeng.ray.framework.core.util.exception.BaseException;

/**
 * 验证码错误
 */
public class PinCodeErrorException extends BaseException {

    /**
     * 构造函数。
     */
    public PinCodeErrorException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public PinCodeErrorException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public PinCodeErrorException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public PinCodeErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
