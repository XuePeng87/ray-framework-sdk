package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysOpLog;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作日志的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
public interface SysOpLogService extends IService<SysOpLog> {

    /**
     * 创建操作日志。
     *
     * @param sysOpLog 操作日志实体类。
     */
    void create(final SysOpLog sysOpLog);

    /**
     * 根据条件分页查询操作日志。
     *
     * @param page     分页信息。
     * @param sysOpLog 查询条件。
     * @return 日志信息。
     */
    Page<SysOpLog> findByPageAndCondition(final Page<SysOpLog> page, final SysOpLog sysOpLog);

    /**
     * 根据主键查询操作内容。
     *
     * @param id 主键。
     * @return 操作日志内容。
     */
    String findContent(final Long id);

}