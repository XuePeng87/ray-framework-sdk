package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysRoleFunctionRelation;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysRoleFunctionRelationMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysRoleFunctionRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色与功能关系的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
@Service
public class SysRoleFunctionRelationServiceImpl extends ServiceImpl<SysRoleFunctionRelationMapper, SysRoleFunctionRelation> implements SysRoleFunctionRelationService {

    /**
     * 根据角色主键删除角色与功能项的关系。
     *
     * @param roleId 角色主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByRoleId(final Long roleId) {
        final QueryWrapper<SysRoleFunctionRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleFunctionRelation::getRoleId, roleId);
        return super.remove(wrapper);
    }

    /**
     * 根据角色主键批量删除角色与功能项的关系。
     *
     * @param roleIds 角色主键集合。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByRoleIds(final List<Long> roleIds) {
        final QueryWrapper<SysRoleFunctionRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysRoleFunctionRelation::getRoleId, roleIds);
        return super.remove(wrapper);
    }

    /**
     * 根据功能项主键删除角色与功能项的关系。
     *
     * @param functionId 角色主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByFunctionId(final Long functionId) {
        final QueryWrapper<SysRoleFunctionRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleFunctionRelation::getFunctionId, functionId);
        return super.remove(wrapper);
    }

    /**
     * 根据角色主键查询角色与功能项的关系。
     *
     * @param roleIds 角色主键集合。
     * @return 角色与功能项的关系。
     */
    @Override
    public List<SysRoleFunctionRelation> findByRoleIds(final List<Long> roleIds) {
        final QueryWrapper<SysRoleFunctionRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysRoleFunctionRelation::getRoleId, roleIds);
        return super.list(wrapper);
    }

}