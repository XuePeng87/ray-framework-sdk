package cc.xuepeng.ray.framework.sdk.mgt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统操作表
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-26
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class SysOpLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作账号
     */
    private String account;

    /**
     * 模块
     */
    private String module;

    /**
     * 描述
     */
    private String description;

    /**
     * 访问IP
     */
    private String srcIp;

    /**
     * 创建时间
     */
    private LocalDateTime createTime = LocalDateTime.now();

    /**
     * 从创建时间开始。
     */
    @TableField(exist = false)
    private transient LocalDateTime startCreateTime = LocalDateTime.now();

    /**
     * 到创建时间结束。
     */
    @TableField(exist = false)
    private transient LocalDateTime endCreateTime = LocalDateTime.now();

    /**
     * 从创建时间开始。
     */
    @TableField(exist = false)
    private transient LocalDateTime startModifyTime = LocalDateTime.now();

    /**
     * 到创建时间结束。
     */
    @TableField(exist = false)
    private transient LocalDateTime endModifyTime = LocalDateTime.now();

    /**
     * 日志详情。
     */
    @TableField(exist = false)
    private SysOpLogContent sysOpLogContent = new SysOpLogContent();

}
