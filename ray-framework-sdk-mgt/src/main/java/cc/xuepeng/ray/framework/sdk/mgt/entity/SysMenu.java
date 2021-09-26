package cc.xuepeng.ray.framework.sdk.mgt.entity;

import cc.xuepeng.ray.framework.sdk.mgt.enums.FunctionType;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统菜单表
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-26
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends Function {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数。
     */
    public SysMenu() {
        super.functionType = FunctionType.MENU;
    }

    /**
     * 父级主键
     */
    private Long menuPid;

    /**
     * 标题
     */
    private String menuTitle;

    /**
     * 英文标题。
     */
    private String menuTitleEn;

    /**
     * 图标
     */
    private String menuIcon;

    /**
     * 是否缓存。
     */
    private Boolean menuCache;

    /**
     * 路径
     */
    private String menuPath;

    /**
     * 层级
     */
    private Integer menuLevel;

    /**
     * 排序
     */
    private Integer menuSort;

    /**
     * 备注
     */
    private String menuRemark;

    /**
     * 是否内置
     */
    private Boolean fixed;

    /**
     * 子菜单。
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();

    /**
     * 按钮。
     */
    @TableField(exist = false)
    private List<SysButton> buttons = new ArrayList<>();


}
