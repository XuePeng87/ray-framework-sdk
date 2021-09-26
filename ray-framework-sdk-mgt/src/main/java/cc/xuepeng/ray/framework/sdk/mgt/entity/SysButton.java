package cc.xuepeng.ray.framework.sdk.mgt.entity;

import cc.xuepeng.ray.framework.sdk.mgt.enums.FunctionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 * 系统按钮表
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-26
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysButton extends Function {

    private static final long serialVersionUID = 1L;

    /**
     * 构造函数。
     */
    public SysButton() {
        super.functionType = FunctionType.BUTTON;
    }

    /**
     * 菜单主键
     */
    private Long menuId;

    /**
     * 名称
     */
    private String buttonTitle;

    /**
     * 图标
     */
    private String buttonIcon;

    /**
     * 备注
     */
    private String buttonRemark;


}
