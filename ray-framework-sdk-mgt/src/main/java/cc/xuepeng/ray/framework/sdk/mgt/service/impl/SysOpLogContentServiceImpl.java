package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysOpLogContent;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysOpLogContentMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysOpLogContentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志内容的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
@Service
public class SysOpLogContentServiceImpl extends ServiceImpl<SysOpLogContentMapper, SysOpLogContent> implements SysOpLogContentService {

    /**
     * 创建操作日志内容。
     *
     * @param sysOpLogContent 操作日志内容的实体类。
     * @return 是否创建成功。
     */
    @Override
    public boolean create(final SysOpLogContent sysOpLogContent) {
        return super.save(sysOpLogContent);
    }

    /**
     * 根据主键查询一个日志的操作内容。
     *
     * @param logId 日志主键。
     * @return 日志的操作内容。
     */
    @Override
    public String findContentByLogId(final Long logId) {
        final QueryWrapper<SysOpLogContent> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysOpLogContent::getLogId, logId);
        return super.getOne(wrapper).getContent();
    }

}