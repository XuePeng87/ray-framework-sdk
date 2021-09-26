package cc.xuepeng.ray.framework.sdk.mgt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统字典表
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典类型
     */
    private Long dictType;

    /**
     * 字典标签
     */
    private String dictCode;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典排序
     */
    private Integer dictSort;

    /**
     * 样式属性（其他样式扩展）
     */
    private String dictCssClass;

    /**
     * 表格回显样式
     */
    private String dictListClass;

    /**
     * 是否默认
     */
    private Boolean dictDefault;

    /**
     * 是否有效
     */
    private Boolean dictEnabled;

    /**
     * 备注
     */
    private String dictRemark;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String modifyUser;

    /**
     * 更新时间
     */
    private LocalDateTime modifyTime;


}
