package cc.xuepeng.ray.framework.sdk.mgt.service.impl.formatter;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysMenu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户菜单格式化器。
 *
 * @author xuepeng
 */
@Service("sysMenuLevelFormatter")
public class SysMenuLevelFormatterImpl implements SysMenuLevelFormatter {

    /**
     * 格式化用户菜单。
     *
     * @param sysMenus 菜单信息。
     * @return 菜单信息。
     */
    @Override
    public List<SysMenu> format(final List<SysMenu> sysMenus) {
        final List<SysMenu> roots = sysMenus.stream()
                .filter(menu -> menu.getMenuPid() == 0)
                .collect(Collectors.toList());
        roots.forEach(root -> createMenuTree(root, sysMenus));
        return roots;
    }

    /**
     * 递归创建菜单层级。
     *
     * @param parent 父菜单。
     * @param nodes  菜单信息。
     */
    private void createMenuTree(final SysMenu parent, final List<SysMenu> nodes) {
        nodes.stream()
                .filter(node -> node.getMenuPid().equals(parent.getId()))
                .forEach(node -> {
                    parent.getChildren().add(node);
                    createMenuTree(node, nodes);
                });
    }

}
