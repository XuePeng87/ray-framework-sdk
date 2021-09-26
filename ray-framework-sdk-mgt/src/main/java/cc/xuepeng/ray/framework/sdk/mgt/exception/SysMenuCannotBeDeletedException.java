package cc.xuepeng.ray.framework.sdk.mgt.exception;

import cc.xuepeng.ray.framework.core.util.exception.BaseException;

/**
 * 菜单无法删除的异常类。
 * 当删除菜单时，菜单下还有关联的按钮，则会抛出该异常。
 *
 * @author xuepeng
 */
public class SysMenuCannotBeDeletedException extends BaseException {

    /**
     * 构造函数。
     */
    public SysMenuCannotBeDeletedException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public SysMenuCannotBeDeletedException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public SysMenuCannotBeDeletedException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public SysMenuCannotBeDeletedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
