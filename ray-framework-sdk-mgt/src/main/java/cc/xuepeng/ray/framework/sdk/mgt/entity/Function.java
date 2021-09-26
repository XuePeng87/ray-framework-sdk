package cc.xuepeng.ray.framework.sdk.mgt.entity;

import cc.xuepeng.ray.framework.sdk.mgt.enums.FunctionType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 功能的父类。
 * 被Menu和Button继承。
 *
 * @author xuepeng
 */
@Data
@ToString
@EqualsAndHashCode
public class Function implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 功能类型。
     */
    @TableField(exist = false)
    FunctionType functionType;

}
