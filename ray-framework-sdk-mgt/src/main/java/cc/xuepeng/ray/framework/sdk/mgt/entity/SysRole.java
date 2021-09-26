package cc.xuepeng.ray.framework.sdk.mgt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统角色表
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-26
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 编号
     */
    private String roleCode;

    /**
     * 名称
     */
    private String roleName;

    /**
     * 备注
     */
    private String roleRemark;

    /**
     * 是否内置
     */
    private Boolean fixed;

    /**
     * 是否删除。
     */
    private Boolean deleted;

    /**
     * 创建人。
     */
    private String createUser;

    /**
     * 修改人。
     */
    private String modifyUser;

    /**
     * 创建时间。
     */
    private LocalDateTime createTime;

    /**
     * 修改时间。
     */
    private LocalDateTime modifyTime;

}
