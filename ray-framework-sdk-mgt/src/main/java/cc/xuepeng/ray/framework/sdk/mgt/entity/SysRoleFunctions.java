package cc.xuepeng.ray.framework.sdk.mgt.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色下的功能。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
public class SysRoleFunctions implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色下的菜单。
     */
    private List<Long> menus = new ArrayList<>();
    /**
     * 角色下的按钮。
     */
    private List<Long> buttons = new ArrayList<>();

    /**
     * 添加菜单。
     *
     * @param menu 菜单。
     */
    public void addMenu(final Long menu) {
        menus.add(menu);
    }

    /**
     * 添加角色。
     *
     * @param button 角色。
     */
    public void addButton(final Long button) {
        buttons.add(button);
    }

}
