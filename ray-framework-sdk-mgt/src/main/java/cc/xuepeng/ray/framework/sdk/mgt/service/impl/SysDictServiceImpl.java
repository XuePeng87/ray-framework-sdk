package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysDict;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysDictMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * SysDict的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-27
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    /**
     * 创建SysDict。
     *
     * @param sysDict SysDict实体类。
     * @return 是否创建成功。
     */
    @Override
    @Transactional
    public boolean create(final SysDict sysDict) {
        isDefaultDict(sysDict);
        return super.save(sysDict);
    }

    /**
     * 修改SysDict。
     *
     * @param sysDict SysDict实体类。
     * @return 是否修改成功。
     */
    @Override
    @Transactional
    public boolean update(final SysDict sysDict) {
        isDefaultDict(sysDict);
        return super.updateById(sysDict);
    }

    /**
     * 根据主键删除SysDict。
     *
     * @param id SysDict主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteById(final Long id) {
        SysDict sysDict = new SysDict();
        sysDict.setId(id);
        sysDict.setDeleted(Boolean.TRUE);
        return super.updateById(sysDict);
    }

    /**
     * 根据主键查找SysDict。
     *
     * @param id SysDict主键。
     * @return SysDict信息。
     */
    @Override
    public SysDict findById(final Long id) {
        return super.getById(id);
    }

    /**
     * 根据类型主键查找SysDict。
     *
     * @param typeId 类型主键。
     * @return SysDict集合。
     */
    @Override
    public List<SysDict> findByTypeId(final Long typeId) {
        final QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .orderByAsc(SysDict::getDictSort)
                .orderByDesc(SysDict::getCreateTime)
                .eq(SysDict::getDictType, typeId)
                .eq(SysDict::getDictEnabled, Boolean.TRUE)
                .eq(SysDict::getDeleted, Boolean.FALSE);
        return super.list(queryWrapper);
    }

    /**
     * 根据条件分页查询SysDict。
     *
     * @param page    分页信息。
     * @param sysDict 查询条件。
     * @return 分页后的SysDict信息集合。
     */
    @Override
    public Page<SysDict> findByPageAndCondition(final Page<SysDict> page, final SysDict sysDict) {
        final QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        final LambdaQueryWrapper<SysDict> criteria = queryWrapper.lambda();
        criteria.orderByAsc(SysDict::getDictSort)
                .orderByDesc(SysDict::getCreateTime)
                .eq(SysDict::getDictEnabled, Boolean.TRUE)
                .eq(SysDict::getDeleted, Boolean.FALSE);
        criteria.eq(SysDict::getDictType, sysDict.getDictType());
        if (ObjectUtils.isNotEmpty(sysDict.getDictDefault())) {
            criteria.eq(SysDict::getDictDefault, sysDict.getDictDefault());
        }
        if (StringUtils.isNotEmpty(sysDict.getDictCode())) {
            criteria.like(SysDict::getDictCode, sysDict.getDictCode());
        }
        if (StringUtils.isNotEmpty(sysDict.getDictValue())) {
            criteria.like(SysDict::getDictValue, sysDict.getDictValue());
        }
        return super.page(page, queryWrapper);
    }

    /**
     * 是否是默认字典。
     *
     * @param sysDict 字典信息。
     */
    private void isDefaultDict(SysDict sysDict) {
        if (BooleanUtils.isTrue(sysDict.getDictDefault())) {
            final SysDict dict = new SysDict();
            dict.setDictDefault(Boolean.FALSE);
            final UpdateWrapper<SysDict> wapper = new UpdateWrapper<>();
            wapper.lambda()
                    .eq(SysDict::getDictDefault, Boolean.TRUE)
                    .eq(SysDict::getDictType, sysDict.getDictType());
            super.update(dict, wapper);
        }
    }

}