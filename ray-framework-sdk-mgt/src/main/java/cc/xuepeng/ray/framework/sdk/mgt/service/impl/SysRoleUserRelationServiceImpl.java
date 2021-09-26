package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysRoleUserRelation;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysRoleUserRelationMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysRoleUserRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色与用户关系的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
@Service
public class SysRoleUserRelationServiceImpl extends ServiceImpl<SysRoleUserRelationMapper, SysRoleUserRelation> implements SysRoleUserRelationService {

    /**
     * 根据角色主键删除角色与人员的关系。
     *
     * @param roleId 角色主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByRoleId(final Long roleId) {
        final QueryWrapper<SysRoleUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleUserRelation::getRoleId, roleId);
        return super.remove(wrapper);
    }

    /**
     * 根据角色主键批量删除角色与人员的关系。
     *
     * @param roleIds 角色主键集合。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByRoleIds(final List<Long> roleIds) {
        final QueryWrapper<SysRoleUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysRoleUserRelation::getRoleId, roleIds);
        return super.remove(wrapper);
    }

    /**
     * 根据用户主键删除角色与人员的关系。
     *
     * @param userId 用户主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByUserId(final Long userId) {
        final QueryWrapper<SysRoleUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleUserRelation::getUserId, userId);
        return super.remove(wrapper);
    }

    /**
     * 根据用户主键批量删除角色与人员的关系。
     *
     * @param userIds 用户主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByUserIds(final List<Long> userIds) {
        final QueryWrapper<SysRoleUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysRoleUserRelation::getUserId, userIds);
        return super.remove(wrapper);
    }

    /**
     * 根据用户主键查询角色主键集合。
     *
     * @param userId 用户主键。
     * @return 角色主键集合。
     */
    @Override
    public List<Long> findRoleIdsByUserId(final Long userId) {
        // 查询用户拥有的角色
        final QueryWrapper<SysRoleUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleUserRelation::getUserId, userId);
        final List<SysRoleUserRelation> sysRoleUserRelations = super.list(wrapper);
        // 获取角色主键并去重
        return sysRoleUserRelations.stream()
                .map(SysRoleUserRelation::getRoleId)
                .distinct()
                .collect(Collectors.toList());
    }

}