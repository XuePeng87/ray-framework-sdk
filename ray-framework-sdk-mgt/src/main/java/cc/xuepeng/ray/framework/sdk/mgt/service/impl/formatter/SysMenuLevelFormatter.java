package cc.xuepeng.ray.framework.sdk.mgt.service.impl.formatter;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysMenu;

import java.util.List;

/**
 * 用户菜单格式化器。
 *
 * @author xuepeng
 */
public interface SysMenuLevelFormatter {

    /**
     * 格式化用户菜单。
     *
     * @param sysMenus 菜单信息。
     * @return 菜单信息。
     */
    List<SysMenu> format(final List<SysMenu> sysMenus);

}
