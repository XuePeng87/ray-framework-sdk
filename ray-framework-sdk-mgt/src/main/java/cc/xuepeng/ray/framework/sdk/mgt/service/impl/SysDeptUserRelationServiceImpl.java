package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysDeptUserRelation;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysDeptUserRelationMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysDeptUserRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门与用户关系的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-03-01
 */
@Service
public class SysDeptUserRelationServiceImpl extends ServiceImpl<SysDeptUserRelationMapper, SysDeptUserRelation> implements SysDeptUserRelationService {

    /**
     * 根据部门主键删除部门与人员的关系。
     *
     * @param deptId 部门主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByDeptId(final Long deptId) {
        final QueryWrapper<SysDeptUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysDeptUserRelation::getDeptId, deptId);
        return super.remove(wrapper);
    }

    /**
     * 根据部门主键批量删除部门与人员的关系。
     *
     * @param deptIds 部门主键集合。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByDeptIds(final List<Long> deptIds) {
        final QueryWrapper<SysDeptUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysDeptUserRelation::getDeptId, deptIds);
        return super.remove(wrapper);
    }

    /**
     * 根据用户主键删除部门与人员的关系。
     *
     * @param userId 用户主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByUserId(final Long userId) {
        final QueryWrapper<SysDeptUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysDeptUserRelation::getUserId, userId);
        return super.remove(wrapper);
    }

    /**
     * 根据用户主键批量删除部门与人员的关系。
     *
     * @param userIds 用户主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByUserIds(final List<Long> userIds) {
        final QueryWrapper<SysDeptUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysDeptUserRelation::getUserId, userIds);
        return super.remove(wrapper);
    }

    /**
     * 根据用户主键批量删除部门与人员的关系。
     *
     * @param userId 用户主键。
     * @return 是否删除成功。
     */
    @Override
    public List<Long> findDeptIdsByUserId(final Long userId) {
        // 查询用户拥有的部门
        final QueryWrapper<SysDeptUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysDeptUserRelation::getUserId, userId);
        final List<SysDeptUserRelation> sysDeptUserRelations = super.list(wrapper);
        // 获取部门主键并去重
        return sysDeptUserRelations.stream()
                .map(SysDeptUserRelation::getDeptId)
                .distinct()
                .collect(Collectors.toList());
    }

}