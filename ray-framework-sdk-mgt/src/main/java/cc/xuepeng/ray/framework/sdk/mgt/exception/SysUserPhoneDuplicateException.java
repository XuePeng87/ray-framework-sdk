package cc.xuepeng.ray.framework.sdk.mgt.exception;

import cc.xuepeng.ray.framework.core.util.exception.BaseException;

/**
 * 用户电话号码重复的异常类。
 *
 * @author xuepeng
 */
public class SysUserPhoneDuplicateException extends BaseException {

    /**
     * 构造函数。
     */
    public SysUserPhoneDuplicateException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysUserPhoneDuplicateException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysUserPhoneDuplicateException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysUserPhoneDuplicateException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
