package cc.xuepeng.ray.framework.sdk.mgt.exception;

import cc.xuepeng.ray.framework.core.util.exception.BaseException;

/**
 * 一分钟内连续发送验证码异常。
 *
 * @author xuepeng
 */
public class PinCodeIntervalException extends BaseException {

    /**
     * 构造函数。
     */
    public PinCodeIntervalException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public PinCodeIntervalException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public PinCodeIntervalException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public PinCodeIntervalException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
