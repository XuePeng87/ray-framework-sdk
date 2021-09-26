package cc.xuepeng.ray.framework.sdk.mgt.exception;

import cc.xuepeng.ray.framework.core.util.exception.BaseException;

/**
 * 部门无法删除的异常类。
 * 当删除部门时，部门下还有关联的人员，则会抛出该异常。
 *
 * @author xuepeng
 */
public class SysDeptCannotBeDeletedException extends BaseException {

    /**
     * 构造函数。
     */
    public SysDeptCannotBeDeletedException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysDeptCannotBeDeletedException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysDeptCannotBeDeletedException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysDeptCannotBeDeletedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
