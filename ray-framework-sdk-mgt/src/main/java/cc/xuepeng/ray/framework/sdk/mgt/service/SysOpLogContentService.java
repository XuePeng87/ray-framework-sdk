package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysOpLogContent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作日志内容的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
public interface SysOpLogContentService extends IService<SysOpLogContent> {

    /**
     * 创建操作日志内容。
     *
     * @param sysOpLogContent 操作日志内容的实体类。
     * @return 是否创建成功。
     */
    boolean create(final SysOpLogContent sysOpLogContent);

    /**
     * 根据主键查询一个日志的操作内容。
     *
     * @param logId 日志主键。
     * @return 日志的操作内容。
     */
    String findContentByLogId(final Long logId);

}