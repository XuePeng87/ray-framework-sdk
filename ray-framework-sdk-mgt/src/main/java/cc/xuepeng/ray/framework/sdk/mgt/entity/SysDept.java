package cc.xuepeng.ray.framework.sdk.mgt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统部门表
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-26
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String deptName;

    /**
     * 父部门主键
     */
    private Long deptPid;

    /**
     * 电话
     */
    private String deptPhoneNumber;

    /**
     * 级别
     */
    private Integer deptLevel;

    /**
     * 排序
     */
    private Integer deptSort;

    /**
     * 备注
     */
    private String deptRemark;

    /**
     * 状态：0=停用；1=启用；
     */
    private Boolean deptAvailabled;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String modifyUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 子部门。
     */
    @TableField(exist = false)
    private List<SysDept> children = new ArrayList<>();

}
