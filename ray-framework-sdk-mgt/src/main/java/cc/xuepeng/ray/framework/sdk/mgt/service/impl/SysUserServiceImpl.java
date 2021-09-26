package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.core.util.biz.ExistsUtil;
import cc.xuepeng.ray.framework.sdk.mgt.config.MgtConfiguration;
import cc.xuepeng.ray.framework.sdk.mgt.entity.*;
import cc.xuepeng.ray.framework.sdk.mgt.exception.*;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysUserMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.*;
import cc.xuepeng.ray.framework.sdk.mgt.service.impl.security.PasswordStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * User的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 创建用户。
     * 如果用户已存在，抛出UserAccountDuplicateException。
     *
     * @param sysUser 用户实体类。
     * @return 是否创建成功。
     */
    @Override
    @Transactional
    public boolean create(final SysUser sysUser) {
        // 创建用户时先判断账号是否已经存在
        if (accountExists(0L, sysUser.getUserAccount())) {
            throw new SysUserAccountDuplicateException("用户账号已存在。");
        }
        // 创建用户时先判断电话号码是否已经存在
        if (phoneExists(0L, sysUser.getUserPhone())) {
            throw new SysUserPhoneDuplicateException("用户电话号码已存在。");
        }
        // 创建用户时先判断电子邮件是否已经存在
        if (emailExists(0L, sysUser.getUserEmail())) {
            throw new SysUserEmailDuplicateException("用户电子邮件已存在。");
        }
        // 创建用户时使用默认密码
        sysUser.setUserSecret(md5PasswordStrategy.encode(mgtConfiguration.getDefaultPassword()));
        sysUser.setUserAvailabled(Boolean.TRUE);
        sysUser.setDeleted(Boolean.FALSE);
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setModifyTime(LocalDateTime.now());
        // 保存用户
        final boolean result = super.save(sysUser);
        // 保存用户与角色的关系
        saveUserRoleRelation(sysUser);
        return result;
    }

    /**
     * 修改用户。
     *
     * @param sysUser 用户实体类。
     * @return 是否修改成功。
     */
    @Override
    @Transactional
    public boolean update(final SysUser sysUser) {
        // 创建用户时先判断账号是否已经存在
        if (accountExists(sysUser.getId(), sysUser.getUserAccount())) {
            throw new SysUserAccountDuplicateException("用户账号已存在。");
        }
        // 创建用户时先判断电话号码是否已经存在
        if (phoneExists(sysUser.getId(), sysUser.getUserPhone())) {
            throw new SysUserPhoneDuplicateException("用户电话号码已存在。");
        }
        // 创建用户时先判断电子邮件是否已经存在
        if (emailExists(sysUser.getId(), sysUser.getUserEmail())) {
            throw new SysUserEmailDuplicateException("用户电子邮件已存在。");
        }
        sysUser.setModifyTime(LocalDateTime.now());
        // 保存用户与角色的关系
        saveUserRoleRelation(sysUser);
        // 修改用户信息
        return this.updateById(sysUser);
    }

    /**
     * 根据主键删除用户。
     *
     * @param id 用户主键。
     * @return 是否删除成功。
     */
    @Override
    @Transactional
    public boolean deleteById(final Long id) {
        // 删除用户与角色的关系。
        deleteUserRole(id);
        // 删除用户
        final SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setDeleted(Boolean.TRUE);
        sysUser.setModifyTime(LocalDateTime.now());
        return this.update(sysUser);
    }

    /**
     * 根据主键批量删除用户。
     *
     * @param ids 用户主键集合。
     * @return 是否删除成功。
     */
    @Override
    @Transactional
    public boolean deleteByIds(final List<Long> ids) {
        // 批量删除用户与角色的关系
        deleteUserRole(ids);
        // 批量删除用户
        final SysUser sysUser = new SysUser();
        sysUser.setDeleted(Boolean.TRUE);
        sysUser.setModifyTime(LocalDateTime.now());
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysUser::getId, ids);
        return super.update(sysUser, wrapper);
    }

    /**
     * 修改密码。
     *
     * @param id        用户主键。
     * @param oldSecret 旧密码。
     * @param newSecret 新密码。
     * @return 是否修改成功。
     */
    @Override
    public boolean updateSecret(final Long id, final String oldSecret, final String newSecret) {
        // 判断旧密码是否正确。
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getId, id).eq(SysUser::getUserSecret, oldSecret);
        if (super.count(wrapper) == 0) {
            throw new SysUserOldSecretIncorrectException("旧密码不正确。");
        }
        // 更新密码。
        return this.updateSecret(id, newSecret);
    }

    /**
     * 修改密码，用于找回密码时直接写入新密码。
     *
     * @param id     用户主键。
     * @param secret 新密码。
     * @return 是否修改成功。
     */
    @Override
    public boolean updateSecret(final Long id, final String secret) {
        // 更新密码。
        final SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setUserSecret(secret);
        sysUser.setModifyTime(LocalDateTime.now());
        return this.updateById(sysUser);
    }

    /**
     * 查询要创建的账号是否已存在。
     *
     * @param id      用户主键。
     * @param account 账号。
     * @return 是否已存在。
     */
    @Override
    public boolean accountExists(final Long id, final String account) {
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUserAccount, account);
        final List<SysUser> sysUsers = super.list(wrapper);
        return ExistsUtil.exists(sysUsers, id == 0 ? StringUtils.EMPTY : id.toString(), "Id");
    }

    /**
     * 查询要创建的电话号码是否已经存在。
     *
     * @param id    用户主键。
     * @param phone 电话号码。
     * @return 是否已存在。
     */
    @Override
    public boolean phoneExists(final Long id, final String phone) {
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUserPhone, phone);
        final List<SysUser> sysUsers = super.list(wrapper);
        return ExistsUtil.exists(sysUsers, id == 0 ? StringUtils.EMPTY : id.toString(), "Id");
    }

    /**
     * 查询要创建的电子邮箱是否已经存在。
     *
     * @param id    用户主键。
     * @param email 电子邮箱。
     * @return 是否已存在。
     */
    @Override
    public boolean emailExists(final Long id, final String email) {
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUserEmail, email);
        final List<SysUser> sysUsers = super.list(wrapper);
        return ExistsUtil.exists(sysUsers, id == 0 ? StringUtils.EMPTY : id.toString(), "Id");
    }

    /**
     * 根据帐号查询用户信息。
     *
     * @param account 账号。
     * @return 用户信息。
     */
    @Override
    public SysUser findByAccount(final String account) {
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysUser::getUserAccount, account);
        final SysUser sysUser = super.getOne(wrapper);
        if (sysUser == null) {
            throw new SysUserNotFoundException("根据账号[" + account + "]为能查询到用户");
        }
        return findById(sysUser.getId());
    }


    /**
     * 重置密码。
     *
     * @param id 用户主键。
     * @return 是否重置成功。
     */
    @Override
    public boolean resetSecret(final Long id) {
        final SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setUserSecret(md5PasswordStrategy.encode(mgtConfiguration.getDefaultPassword()));
        sysUser.setModifyTime(LocalDateTime.now());
        return super.updateById(sysUser);
    }

    /**
     * 根据主键查找用户，查询用户角色，查询角色的菜单和按钮。
     *
     * @param id 用户主键。
     * @return 用户信息。
     */
    @Override
    public SysUser findById(final Long id) {
        // 查询用户信息
        final SysUser sysUser = super.getById(id);
        // 查询用户所拥有的角色
        final List<SysRole> sysRoles = findRolesById(id);
        sysUser.setRoles(sysRoles);
        // 根据角色查询菜单信息
        if (CollectionUtils.isNotEmpty(sysRoles)) {
            final List<Long> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
            final List<SysMenu> menus = sysMenuService.findByRoleIds(roleIds);
            sysUser.setMenus(menus);
        }
        // 根据角色查询部门信息
        final List<Long> deptIds = sysDeptUserRelationService.findDeptIdsByUserId(id);
        if (CollectionUtils.isNotEmpty(deptIds)) {
            final List<SysDept> departments = sysDeptService.findByIds(deptIds);
            sysUser.setDepartments(departments);
        }
        return sysUser;
    }

    /**
     * 根据主键批量查找用户。
     *
     * @param ids 用户主键。
     * @return 用户信息集合。
     */
    @Override
    public List<SysUser> findByIds(final List<Long> ids) {
        // 只需要用户信息，不需要角色和功能。
        return super.listByIds(ids);
    }

    /**
     * 查询没有部门的用户。
     *
     * @param deptId 部门主键。
     * @return 没有部门的用户（但把当前部门的用户查询出来）。
     */
    @Override
    public List<SysUser> findNoDepartmentUsers(final Long deptId) {
        return super.getBaseMapper().findNoDepartmentUsers(deptId);
    }

    /**
     * 查询全部用户。
     *
     * @return 用户信息集合。
     */
    @Override
    public List<SysUser> findAll() {
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysUser::getDeleted, Boolean.FALSE)
                .eq(SysUser::getUserAvailabled, Boolean.TRUE)
                .eq(SysUser::getFixed, Boolean.FALSE)
                .orderByDesc(SysUser::getCreateTime);
        return super.list(wrapper);
    }

    /**
     * 根据条件分页查询用户。
     *
     * @param page 分页信息。
     * @param user 查询条件。
     * @return 分页后的用户信息集合。
     */
    @Override
    public Page<SysUser> findByPageAndCondition(final Page<SysUser> page, final SysUser user) {
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        final LambdaQueryWrapper<SysUser> criteria = wrapper.lambda();
        criteria.orderByDesc(SysUser::getCreateTime)
                .eq(SysUser::getUserAvailabled, Boolean.TRUE)
                .eq(SysUser::getDeleted, Boolean.FALSE)
                .eq(SysUser::getFixed, Boolean.FALSE);
        if (StringUtils.isNotEmpty(user.getUserAccount())) {
            criteria.like(SysUser::getUserAccount, user.getUserAccount());
        }
        if (StringUtils.isNotEmpty(user.getUserName())) {
            criteria.like(SysUser::getUserName, user.getUserName());
        }
        if (StringUtils.isNotEmpty(user.getUserPhone())) {
            criteria.like(SysUser::getUserPhone, user.getUserPhone());
        }
        if (StringUtils.isNotEmpty(user.getUserEmail())) {
            criteria.like(SysUser::getUserEmail, user.getUserEmail());
        }
        final Page<SysUser> result = super.page(page, wrapper);
        final List<SysUser> sysUsers = result.getRecords();
        for (SysUser u : sysUsers) {
            // 查询用户所拥有的角色
            u.setRoles(findRolesById(u.getId()));
        }
        return result;
    }

    /**
     * 根据邮箱查询用户主键。
     *
     * @param email 邮箱。
     * @return 用户主键。
     */
    @Override
    public Long findIdByEmail(final String email) {
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysUser::getUserEmail, email)
                .eq(SysUser::getDeleted, Boolean.FALSE);
        return super.getOne(wrapper).getId();
    }

    /**
     * 根据电话号查询用户主键。
     *
     * @param phone 电话号。
     * @return 用户主键。
     */
    @Override
    public Long findIdByPhone(final String phone) {
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysUser::getUserPhone, phone)
                .eq(SysUser::getDeleted, Boolean.FALSE);
        return super.getOne(wrapper).getId();
    }

    /**
     * 根据邮箱查询用户数量。
     *
     * @param email 邮箱。
     * @return 用户数量。
     */
    @Override
    public long countByEmail(final String email) {
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysUser::getUserEmail, email)
                .eq(SysUser::getDeleted, Boolean.FALSE);
        return super.count(wrapper);
    }

    /**
     * 根据电话号查询用户数量。
     *
     * @param phone 电话号。
     * @return 用户数量。
     */
    @Override
    public long countByPhone(final String phone) {
        final QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysUser::getUserPhone, phone)
                .eq(SysUser::getDeleted, Boolean.FALSE);
        return super.count(wrapper);
    }

    /**
     * 根据主键查询用户拥有的角色。
     *
     * @param id 主键。
     * @return 用户拥有的角色。
     */
    private List<SysRole> findRolesById(final Long id) {
        final List<Long> roleIds = findRoleIdsById(id);
        if (CollectionUtils.isNotEmpty(roleIds)) {
            return sysRoleService.findByIds(roleIds);
        }
        return new ArrayList<>();
    }

    /**
     * 删除用户与角色的关系。
     *
     * @param id 用户主键。
     */
    private void deleteUserRole(final Long id) {
        // 删除用户与角色的关系
        sysRoleUserRelationService.deleteByUserId(id);
    }

    /**
     * 删除批量用户与角色的关系。
     *
     * @param ids 用户主键集合。
     */
    private void deleteUserRole(final List<Long> ids) {
        // 删除用户与角色的关系
        sysRoleUserRelationService.deleteByUserIds(ids);
    }

    /**
     * 保存用户与角色的关系。
     *
     * @param sysUser 用户信息。
     */
    private void saveUserRoleRelation(final SysUser sysUser) {
        deleteUserRole(sysUser.getId());
        final List<SysRole> sysRoles = sysUser.getRoles();
        if (CollectionUtils.isNotEmpty(sysRoles)) {
            final List<Long> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
            createUserRole(sysUser.getId(), roleIds);
        }
    }

    /**
     * 创建用户与角色的关系。
     *
     * @param id      用户主键。
     * @param roleIds 角色主键集合。
     */
    private void createUserRole(final Long id, final List<Long> roleIds) {
        // 保存用户与角色的关系
        List<SysRoleUserRelation> sysRoleUserRelations = new ArrayList<>();
        for (Long roleId : roleIds) {
            final SysRoleUserRelation sysRoleUserRelation = new SysRoleUserRelation();
            sysRoleUserRelation.setRoleId(roleId);
            sysRoleUserRelation.setUserId(id);
            sysRoleUserRelations.add(sysRoleUserRelation);
        }
        sysRoleUserRelationService.saveBatch(sysRoleUserRelations);
    }

    /**
     * 查询用户所拥有的角色的主键集合。
     *
     * @param id 用户主键。
     * @return 角色主键集合。
     */
    private List<Long> findRoleIdsById(final Long id) {
        return sysRoleUserRelationService.findRoleIdsByUserId(id);
    }

    /**
     * 自动装配角色与用户关系业务处理接口。
     *
     * @param sysRoleUserRelationService 角色与用户关系业务处理接口。
     */
    @Autowired
    public void setSysRoleUserRelationService(SysRoleUserRelationService sysRoleUserRelationService) {
        this.sysRoleUserRelationService = sysRoleUserRelationService;
    }

    /**
     * 自动装配菜单管理的业务处理接口。
     *
     * @param sysMenuService 菜单管理的业务处理接口。
     */
    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    /**
     * 自动装配角色管理的业务处理接口。
     *
     * @param sysRoleService 角色管理的业务处理接口。
     */
    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 自动装配部门管理的业务处理接口。
     *
     * @param sysDeptService 部门管理的业务处理接口。
     */
    @Autowired
    public void setSysDeptService(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    /**
     * 自动装配部门与用户关系业务处理接口。
     *
     * @param sysDeptUserRelationService 部门与用户关系业务处理接口。
     */
    @Autowired
    public void setSysDeptUserRelationService(SysDeptUserRelationService sysDeptUserRelationService) {
        this.sysDeptUserRelationService = sysDeptUserRelationService;
    }

    /**
     * 自动装配密码加密策略。
     *
     * @param md5PasswordStrategy 密码加密策略。
     */
    @Autowired
    public void setMd5PasswordStrategy(PasswordStrategy md5PasswordStrategy) {
        this.md5PasswordStrategy = md5PasswordStrategy;
    }

    /**
     * 自动装配用户默认密码。
     *
     * @param mgtConfiguration 用户默认密码。
     */
    @Autowired
    public void setMgtConfiguration(MgtConfiguration mgtConfiguration) {
        this.mgtConfiguration = mgtConfiguration;
    }

    /**
     * 角色与用户关系业务处理接口。
     */
    private SysRoleUserRelationService sysRoleUserRelationService;
    /**
     * 菜单管理的业务处理接口。
     */
    private SysMenuService sysMenuService;
    /**
     * 角色管理的业务处理接口。
     */
    private SysRoleService sysRoleService;
    /**
     * 部门管理的业务处理接口。
     */
    private SysDeptService sysDeptService;
    /**
     * 部门与用户关系业务处理接口。
     */
    private SysDeptUserRelationService sysDeptUserRelationService;
    /**
     * 密码加密策略。
     */
    private PasswordStrategy md5PasswordStrategy;
    /**
     * 用户默认密码。
     */
    private MgtConfiguration mgtConfiguration;

}