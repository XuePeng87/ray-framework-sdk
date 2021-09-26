package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.core.util.biz.ExistsUtil;
import cc.xuepeng.ray.framework.sdk.mgt.entity.*;
import cc.xuepeng.ray.framework.sdk.mgt.enums.FunctionType;
import cc.xuepeng.ray.framework.sdk.mgt.exception.SysRoleCannotBeDeletedException;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysRoleMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    /**
     * 创建角色。
     *
     * @param sysRole 角色实体类。
     * @return 是否创建成功。
     */
    @Override
    public boolean create(final SysRole sysRole) {
        sysRole.setDeleted(Boolean.FALSE);
        sysRole.setCreateTime(LocalDateTime.now());
        sysRole.setModifyTime(LocalDateTime.now());
        return super.save(sysRole);
    }

    /**
     * 修改角色。
     *
     * @param sysRole 角色实体类。
     * @return 是否修改成功。
     */
    @Override
    public boolean update(final SysRole sysRole) {
        sysRole.setModifyTime(LocalDateTime.now());
        return super.updateById(sysRole);
    }

    /**
     * 根据主键删除角色。
     *
     * @param id 角色主键。
     * @return 是否删除成功。
     */
    @Override
    @Transactional
    public boolean deleteById(final Long id) {
        if (hasUser(id)) {
            throw new SysRoleCannotBeDeletedException("角色不能删除，请先删除角色下的用户。");
        }
        // 删除角色与功能的关系
        deleteRoleFunctionById(id);
        // 删除角色
        final SysRole sysRole = new SysRole();
        sysRole.setId(id);
        sysRole.setDeleted(Boolean.TRUE);
        sysRole.setModifyTime(LocalDateTime.now());
        return this.update(sysRole);
    }

    /**
     * 根据主键批量删除角色。
     *
     * @param ids 角色主键集合。
     * @return 是否删除成功。
     */
    @Override
    @Transactional
    public boolean deleteByIds(final List<Long> ids) {
        // 删除角色与人员和菜单的关系
        deleteRoleUserByIds(ids);
        deleteRoleFunctionByIds(ids);
        // 删除角色
        final SysRole sysRole = new SysRole();
        sysRole.setDeleted(Boolean.TRUE);
        final UpdateWrapper<SysRole> wrapper = new UpdateWrapper<>();
        wrapper.lambda().in(SysRole::getId, ids);
        return super.update(sysRole, wrapper);
    }

    /**
     * 保存角色下的用户。
     *
     * @param id      角色主键。
     * @param userIds 用户主键集合。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveRoleUser(final Long id, final List<Long> userIds) {
        // 删除角色与人员的关系
        deleteRoleUserById(id);
        // 保存角色与人员的关系
        final List<SysRoleUserRelation> sysRoleUserRelations = new ArrayList<>();
        for (Long userId : userIds) {
            final SysRoleUserRelation sysRoleUserRelation = new SysRoleUserRelation();
            sysRoleUserRelation.setRoleId(id);
            sysRoleUserRelation.setUserId(userId);
            sysRoleUserRelations.add(sysRoleUserRelation);
        }
        if (CollectionUtils.isEmpty(sysRoleUserRelations)) {
            return true;
        }
        return sysRoleUserRelationService.saveBatch(sysRoleUserRelations);
    }

    /**
     * 保存角色下的功能。
     *
     * @param id        角色主键。
     * @param functions 功能集合。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveRoleFunction(final Long id, final List<Function> functions) {
        // 删除角色与功能的关系
        deleteRoleFunctionById(id);
        // 保存角色与功能的关系
        final List<SysRoleFunctionRelation> sysRoleFunctionRelations = new ArrayList<>();
        for (Function function : functions) {
            final SysRoleFunctionRelation sysRoleFunctionRelation = new SysRoleFunctionRelation();
            sysRoleFunctionRelation.setRoleId(id);
            sysRoleFunctionRelation.setFunctionId(function.getId());
            sysRoleFunctionRelation.setFunctionType(function.getFunctionType().ordinal());
            sysRoleFunctionRelations.add(sysRoleFunctionRelation);
        }
        if (CollectionUtils.isEmpty(sysRoleFunctionRelations)) {
            return true;
        }
        return sysRoleFunctionRelationService.saveBatch(sysRoleFunctionRelations);
    }

    /**
     * @return 获取全部角色。
     */
    @Override
    public List<SysRole> findAll() {
        final QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysRole::getDeleted, Boolean.FALSE)
                .eq(SysRole::getFixed, Boolean.FALSE)
                .orderByDesc(SysRole::getCreateTime);
        return super.list(wrapper);
    }

    /**
     * 根据角色主键批量查找角色。
     *
     * @param ids 角色主键集合。
     * @return 角色信息集合。
     */
    @Override
    public List<SysRole> findByIds(final List<Long> ids) {
        final QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysRole::getId, ids);
        return super.list(wrapper);
    }

    /**
     * 根据条件分页查询用户。
     *
     * @param page 分页信息。
     * @param role 查询条件。
     * @return 分页后的角色信息集合。
     */
    @Override
    public Page<SysRole> findByPageAndCondition(final Page<SysRole> page, final SysRole role) {
        final QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        final LambdaQueryWrapper<SysRole> criteria = wrapper.lambda();
        criteria.orderByDesc(SysRole::getCreateTime);
        if (StringUtils.isNotEmpty(role.getRoleName())) {
            criteria.like(SysRole::getRoleName, role.getRoleName());
        }
        criteria.eq(SysRole::getDeleted, Boolean.FALSE);
        criteria.eq(SysRole::getFixed, Boolean.FALSE);
        return super.page(page, wrapper);
    }

    /**
     * 根据主键查找角色下的用户。
     *
     * @param id 主键。
     * @return 角色下的用户。
     */
    @Override
    public List<Long> findRoleUsersById(final Long id) {
        final QueryWrapper<SysRoleUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleUserRelation::getRoleId, id);
        final List<SysRoleUserRelation> roleUserRelations = sysRoleUserRelationService.list(wrapper);
        final List<Long> userIds = roleUserRelations.stream()
                .map(SysRoleUserRelation::getUserId)
                .distinct()
                .collect(Collectors.toList());
        return Collections.unmodifiableList(userIds);
    }

    /**
     * 根据主键查找角色下的功能。
     *
     * @param id 主键。
     * @return 角色下的功能。
     */
    @Override
    public SysRoleFunctions findRoleFunctionsById(final Long id) {
        // 查询角色下的功能
        final QueryWrapper<SysRoleFunctionRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleFunctionRelation::getRoleId, id);
        final List<SysRoleFunctionRelation> sysRoleFunctionRelations = sysRoleFunctionRelationService.list(wrapper);
        // 查询并封装菜单信息。
        final SysRoleFunctions sysRoleFunctions = new SysRoleFunctions();
        final Set<Long> topMenuIds = sysMenuService.findTopMenuIds();
        for (SysRoleFunctionRelation roleFunctionRelation : sysRoleFunctionRelations) {
            if (roleFunctionRelation.getFunctionType() == FunctionType.MENU.ordinal()) {
                // 判断是否是一级菜单
                if (CollectionUtils.isNotEmpty(topMenuIds) &&
                        !topMenuIds.contains(roleFunctionRelation.getFunctionId())) {
                    sysRoleFunctions.addMenu(roleFunctionRelation.getFunctionId());
                }
            } else {
                sysRoleFunctions.addButton(roleFunctionRelation.getFunctionId());
            }
        }
        return sysRoleFunctions;
    }

    /**
     * 保存角色与API的鉴权关系。
     *
     * @param id   角色主键。
     * @param urls API的路径。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveRoleApi(final Long id, final List<String> urls) {
        // 根据主键删除与API的鉴权关系
        deleteRoleApiById(id);
        // 批量生成关系主键
        final List<SysRoleApiRelation> roleApiRelations = new ArrayList<>();
        for (String url : urls) {
            SysRoleApiRelation roleApiRelation = new SysRoleApiRelation();
            roleApiRelation.setRoleId(id);
            roleApiRelation.setApiUrl(url);
            roleApiRelations.add(roleApiRelation);
        }
        if (CollectionUtils.isEmpty(roleApiRelations)) {
            return true;
        }
        // 保存角色与API的鉴权关系
        return sysRoleApiRelationService.saveBatch(roleApiRelations);
    }

    /**
     * 根据主键查找角色下的API鉴权关系。
     *
     * @param id 角色主键。
     * @return 角色下的API鉴权关系。
     */
    @Override
    public List<String> findRoleApiById(final Long id) {
        return sysRoleApiRelationService.findApisByRoleId(id);
    }

    /**
     * 判断角色下是否有用户存在。
     *
     * @param id 角色主键。
     * @return 角色下是否有用户存在。
     */
    private boolean hasUser(final Long id) {
        final QueryWrapper<SysRoleUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRoleUserRelation::getRoleId, id);
        return sysRoleUserRelationService.count(wrapper) > 0;
    }

    /**
     * 根据主键删除角色与人员的关系。
     *
     * @param id 角色主键。
     */
    private void deleteRoleUserById(final Long id) {
        // 删除角色与人员的关系
        sysRoleUserRelationService.deleteByRoleId(id);
    }

    /**
     * 根据主键删除角色与功能的关系。
     *
     * @param id 角色主键。
     */
    private void deleteRoleFunctionById(final Long id) {
        // 删除角色与功能的关系
        sysRoleFunctionRelationService.deleteByRoleId(id);
    }

    /**
     * 根据主键批量删除角色与人员的关系。
     *
     * @param ids 角色主键集合。
     */
    private void deleteRoleUserByIds(final List<Long> ids) {
        // 删除角色与人员的关系
        sysRoleUserRelationService.deleteByRoleIds(ids);
    }

    /**
     * 根据主键批量删除角色与功能的关系。
     *
     * @param ids 角色主键集合。
     */
    private void deleteRoleFunctionByIds(final List<Long> ids) {
        // 删除角色与功能的关系
        sysRoleFunctionRelationService.deleteByRoleIds(ids);
    }

    /**
     * 根据主键删除角色与API的关系。
     *
     * @param id 角色主键。
     */
    private void deleteRoleApiById(final Long id) {
        sysRoleApiRelationService.deleteByRoleId(id);
    }

    /**
     * @return 查询全部API信息。
     */
    @Override
    public List<String> findAllRoleApi() {
        return sysRoleApiRelationService.findAllApis();
    }

    /**
     * 根据API查询角色编号。
     *
     * @param api API路径。
     * @return 角色编号集合。
     */
    @Override
    public List<String> findRoleCodeByRoleApi(final String api) {
        // 查询API所属的角色。
        final List<Long> roleIds = sysRoleApiRelationService.findRoleIdsByApi(api);
        // 查询角色的Code
        final List<SysRole> roles = findByIds(roleIds);
        return roles.stream().map(SysRole::getRoleCode).distinct().collect(Collectors.toList());
    }

    /**
     * 查询要创建的角色编号是否已经存在。
     *
     * @param id   角色主键。
     * @param code 角色编号。
     * @return 是否已存在。
     */
    @Override
    public boolean codeExists(final Long id, final String code) {
        final QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRole::getRoleCode, code);
        final List<SysRole> roles = super.list(wrapper);
        return ExistsUtil.exists(roles, id.toString(), "Id");
    }

    /**
     * 查询要创建的角色名称是否已经存在。
     *
     * @param id   角色主键。
     * @param name 角色名称。
     * @return 是否已存在。
     */
    @Override
    public boolean nameExists(final Long id, final String name) {
        final QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysRole::getRoleName, name);
        final List<SysRole> roles = super.list(wrapper);
        return ExistsUtil.exists(roles, id.toString(), "Id");
    }

    /**
     * 自动装配菜单服务接口。
     *
     * @param sysMenuService 菜单服务接口。
     */
    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    /**
     * 自动装配角色用户关系服务接口。
     *
     * @param sysRoleUserRelationService 角色用户关系服务接口。
     */
    @Autowired
    public void setSysRoleUserRelationService(SysRoleUserRelationService sysRoleUserRelationService) {
        this.sysRoleUserRelationService = sysRoleUserRelationService;
    }

    /**
     * 自动装配角色功能项关系服务接口。
     *
     * @param sysRoleFunctionRelationService 角色功能项关系服务接口。
     */
    @Autowired
    public void setSysRoleFunctionRelationService(SysRoleFunctionRelationService sysRoleFunctionRelationService) {
        this.sysRoleFunctionRelationService = sysRoleFunctionRelationService;
    }

    /**
     * 自动装配角色API关系服务接口。
     *
     * @param sysRoleApiRelationService 角色API关系服务接口。
     */
    @Autowired
    public void setSysRoleApiRelationService(SysRoleApiRelationService sysRoleApiRelationService) {
        this.sysRoleApiRelationService = sysRoleApiRelationService;
    }

    /**
     * 菜单服务接口。
     */
    private SysMenuService sysMenuService;

    /**
     * 角色用户关系服务接口。
     */
    private SysRoleUserRelationService sysRoleUserRelationService;

    /**
     * 角色功能项关系服务接口。
     */
    private SysRoleFunctionRelationService sysRoleFunctionRelationService;

    /**
     * 角色API关系服务接口。
     */
    private SysRoleApiRelationService sysRoleApiRelationService;

}