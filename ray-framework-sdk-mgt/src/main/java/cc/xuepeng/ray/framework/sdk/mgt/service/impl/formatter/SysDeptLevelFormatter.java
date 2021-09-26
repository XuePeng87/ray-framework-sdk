package cc.xuepeng.ray.framework.sdk.mgt.service.impl.formatter;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysDept;

import java.util.List;

/**
 * 部门格式化器。
 *
 * @author xuepeng
 */
public interface SysDeptLevelFormatter {

    /**
     * 格式化部门。
     *
     * @param sysDepts 部门信息。
     * @return 部门信息。
     */
    List<SysDept> format(final List<SysDept> sysDepts);

}
