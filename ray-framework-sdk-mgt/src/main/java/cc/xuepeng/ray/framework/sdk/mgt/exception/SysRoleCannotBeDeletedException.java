package cc.xuepeng.ray.framework.sdk.mgt.exception;

import cc.xuepeng.ray.framework.core.util.exception.BaseException;

/**
 * 角色无法删除的异常类。
 * 当删除角色时，角色下还有关联的人员，则会抛出该异常。
 *
 * @author xuepeng
 */
public class SysRoleCannotBeDeletedException extends BaseException {

    /**
     * 构造函数。
     */
    public SysRoleCannotBeDeletedException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysRoleCannotBeDeletedException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysRoleCannotBeDeletedException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysRoleCannotBeDeletedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
