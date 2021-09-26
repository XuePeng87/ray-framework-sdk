package cc.xuepeng.ray.framework.sdk.mgt.exception;

import cc.xuepeng.ray.framework.core.util.exception.BaseException;

/**
 * 修改密码发送邮件异常
 */
public class SendingEmailException extends BaseException {

    /**
     * 构造函数。
     */
    public SendingEmailException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SendingEmailException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SendingEmailException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SendingEmailException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
