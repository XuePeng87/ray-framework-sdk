package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysOpLog;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysOpLogMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysOpLogContentService;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysOpLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 操作日志的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
@Service
public class SysOpLogServiceImpl extends ServiceImpl<SysOpLogMapper, SysOpLog> implements SysOpLogService {


    /**
     * 创建操作日志。
     *
     * @param sysOpLog 操作日志实体类。
     */
    @Override
    @Transactional
    public void create(final SysOpLog sysOpLog) {
        super.save(sysOpLog);
        sysOpLog.getSysOpLogContent().setLogId(sysOpLog.getId());
        sysOpLogContentService.create(sysOpLog.getSysOpLogContent());
    }

    /**
     * 根据条件分页查询操作日志。
     *
     * @param page     分页信息。
     * @param sysOpLog 查询条件。
     * @return 日志信息。
     */
    @Override
    public Page<SysOpLog> findByPageAndCondition(final Page<SysOpLog> page, final SysOpLog sysOpLog) {
        final QueryWrapper<SysOpLog> wrapper = new QueryWrapper<>();
        final LambdaQueryWrapper<SysOpLog> criteria = wrapper.lambda();
        if (StringUtils.isNotEmpty(sysOpLog.getAccount())) {
            criteria.like(SysOpLog::getAccount, sysOpLog.getAccount());
        }
        if (StringUtils.isNotEmpty(sysOpLog.getModule())) {
            criteria.like(SysOpLog::getModule, sysOpLog.getModule());
        }
        if (StringUtils.isNotEmpty(sysOpLog.getDescription())) {
            criteria.like(SysOpLog::getDescription, sysOpLog.getDescription());
        }
        if (StringUtils.isNotEmpty(sysOpLog.getSrcIp())) {
            criteria.like(SysOpLog::getSrcIp, sysOpLog.getSrcIp());
        }
        if (ObjectUtils.allNotNull(sysOpLog.getStartCreateTime(), sysOpLog.getEndCreateTime())) {
            criteria.between(
                    SysOpLog::getCreateTime,
                    sysOpLog.getStartCreateTime(),
                    sysOpLog.getEndCreateTime()
            );
        }
        criteria.orderByDesc(SysOpLog::getCreateTime);
        return super.page(page, wrapper);
    }

    /**
     * 根据主键查询操作内容。
     *
     * @param id 主键。
     * @return 操作日志内容。
     */
    @Override
    public String findContent(final Long id) {
        return sysOpLogContentService.findContentByLogId(id);
    }

    /**
     * 自动装配操作日志详情的数据处理接口。
     *
     * @param sysOpLogContentService 操作日志详情的数据处理接口。
     */
    @Autowired
    public void setSysOpLogContentService(SysOpLogContentService sysOpLogContentService) {
        this.sysOpLogContentService = sysOpLogContentService;
    }

    /**
     * 操作日志详情的数据处理接口。
     */
    private SysOpLogContentService sysOpLogContentService;

}
