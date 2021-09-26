package cc.xuepeng.ray.framework.sdk.mgt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 系统角色与功能关系表
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-26
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class SysRoleFunctionRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色主键
     */
    private Long roleId;

    /**
     * 功能主键
     */
    private Long functionId;

    /**
     * 功能类型：0=菜单，1=按钮
     */
    private Integer functionType;


}
