package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysDictType;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysDictTypeMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysDictTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * SysDictType的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-27
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    /**
     * 创建SysDictType。
     *
     * @param sysDictType SysDictType实体类。
     * @return 是否创建成功。
     */
    @Override
    public boolean create(final SysDictType sysDictType) {
        sysDictType.setDeleted(Boolean.FALSE);
        sysDictType.setDictTypeEnabled(Boolean.TRUE);
        sysDictType.setCreateTime(LocalDateTime.now());
        sysDictType.setModifyTime(LocalDateTime.now());
        return super.save(sysDictType);
    }

    /**
     * 修改SysDictType。
     *
     * @param sysDictType SysDictType实体类。
     * @return 是否修改成功。
     */
    @Override
    public boolean update(final SysDictType sysDictType) {
        sysDictType.setModifyTime(LocalDateTime.now());
        return super.updateById(sysDictType);
    }

    /**
     * 根据主键删除SysDictType。
     *
     * @param id SysDictType主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteById(final Long id) {
        SysDictType sysDictType = new SysDictType();
        sysDictType.setId(id);
        sysDictType.setDeleted(Boolean.TRUE);
        sysDictType.setModifyTime(LocalDateTime.now());
        return super.updateById(sysDictType);
    }

    /**
     * @return 查询全部字典类型。
     */
    @Override
    public List<SysDictType> findAll() {
        final QueryWrapper<SysDictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .orderByDesc(SysDictType::getCreateTime)
                .eq(SysDictType::getDictTypeEnabled, Boolean.TRUE)
                .eq(SysDictType::getDeleted, Boolean.FALSE);
        return super.list(queryWrapper);
    }

}