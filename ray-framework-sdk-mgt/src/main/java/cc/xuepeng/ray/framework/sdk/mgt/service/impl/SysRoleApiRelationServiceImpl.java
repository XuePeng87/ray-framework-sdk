package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysRoleApiRelation;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysRoleApiRelationMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysRoleApiRelationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色与API关系的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
@Service
public class SysRoleApiRelationServiceImpl extends ServiceImpl<SysRoleApiRelationMapper, SysRoleApiRelation> implements SysRoleApiRelationService {

    private static final String PASS_URL = "/**";

    /**
     * 根据角色主键删除角色与API的关系。
     *
     * @param roleId 角色主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteByRoleId(final Long roleId) {
        final QueryWrapper<SysRoleApiRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleApiRelation::getRoleId, roleId);
        return super.remove(wrapper);
    }

    /**
     * @return 查询全部的Api信息。
     */
    @Override
    public List<String> findAllApis() {
        final List<SysRoleApiRelation> roleApiRelations = super.list();
        return roleApiRelations.stream()
                .map(SysRoleApiRelation::getApiUrl)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 根据API查询角色主键集合。
     *
     * @param api API地址。
     * @return 角色主键集合。
     */
    @Override
    public List<Long> findRoleIdsByApi(final String api) {
        final QueryWrapper<SysRoleApiRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysRoleApiRelation::getApiUrl, api)
                .or()
                .eq(SysRoleApiRelation::getApiUrl, PASS_URL);
        final List<SysRoleApiRelation> sysRoleApiRelations = super.list(wrapper);
        return sysRoleApiRelations.stream()
                .map(SysRoleApiRelation::getRoleId)
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 根据角色主键查询API信息。
     *
     * @param roleId 角色主键。
     * @return API信息。
     */
    @Override
    public List<String> findApisByRoleId(final Long roleId) {
        final QueryWrapper<SysRoleApiRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleApiRelation::getRoleId, roleId);
        final List<SysRoleApiRelation> sysRoleApiRelations = super.list(wrapper);
        return sysRoleApiRelations.stream()
                .map(SysRoleApiRelation::getApiUrl)
                .distinct()
                .collect(Collectors.toList());
    }

}