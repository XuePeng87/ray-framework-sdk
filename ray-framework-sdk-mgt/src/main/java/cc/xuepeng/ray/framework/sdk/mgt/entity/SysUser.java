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
 * 系统用户表
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-26
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userSecret;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 电话
     */
    private String userPhone;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 头像
     */
    private String userAvator;

    /**
     * 状态：0=停用；1=启用；
     */
    private Boolean userAvailabled;

    /**
     * 备注
     */
    private String userRemark;

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

    /**
     * 角色。
     */
    @TableField(exist = false)
    private transient List<SysRole> roles = new ArrayList<>();

    /**
     * 菜单。
     */
    @TableField(exist = false)
    private transient List<SysMenu> menus = new ArrayList<>();

    /**
     * 部门。
     */
    @TableField(exist = false)
    private transient List<SysDept> departments = new ArrayList<>();


}
