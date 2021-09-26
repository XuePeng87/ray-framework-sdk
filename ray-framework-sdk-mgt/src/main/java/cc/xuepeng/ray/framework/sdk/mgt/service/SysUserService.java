package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统用户的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 创建用户。
     *
     * @param sysUser 用户实体类。
     * @return 是否创建成功。
     */
    boolean create(final SysUser sysUser);

    /**
     * 修改用户。
     *
     * @param sysUser 用户实体类。
     * @return 是否修改成功。
     */
    boolean update(final SysUser sysUser);

    /**
     * 根据主键删除用户。
     *
     * @param id 用户主键。
     * @return 是否删除成功。
     */
    boolean deleteById(final Long id);

    /**
     * 根据主键批量删除用户。
     *
     * @param ids 用户主键集合。
     * @return 是否删除成功。
     */
    boolean deleteByIds(final List<Long> ids);

    /**
     * 修改密码。
     *
     * @param id        用户主键。
     * @param oldSecret 旧密码。
     * @param newSecret 新密码。
     * @return 是否修改成功。
     */
    boolean updateSecret(final Long id, final String oldSecret, final String newSecret);

    /**
     * 修改密码，用于找回密码时直接写入新密码。
     *
     * @param id     用户主键。
     * @param secret 新密码。
     * @return 是否修改成功。
     */
    boolean updateSecret(final Long id, final String secret);

    /**
     * 重置密码。
     *
     * @param id 用户主键。
     * @return 是否重置成功。
     */
    boolean resetSecret(final Long id);

    /**
     * 查询要创建的账号是否已存在。
     *
     * @param id      用户主键。
     * @param account 账号。
     * @return 是否已存在。
     */
    boolean accountExists(final Long id, final String account);

    /**
     * 查询要创建的电话号码是否已经存在。
     *
     * @param id    用户主键。
     * @param phone 电话号码。
     * @return 是否已存在。
     */
    boolean phoneExists(final Long id, final String phone);

    /**
     * 查询要创建的电子邮箱是否已经存在。
     *
     * @param id    用户主键。
     * @param email 电子邮箱。
     * @return 是否已存在。
     */
    boolean emailExists(final Long id, final String email);

    /**
     * 根据帐号查询用户信息。
     *
     * @param account 账号。
     * @return 用户信息。
     */
    SysUser findByAccount(final String account);

    /**
     * 根据主键查找用户，查询用户角色，查询角色的菜单和按钮。
     *
     * @param id 用户主键。
     * @return 用户信息。
     */
    SysUser findById(final Long id);

    /**
     * 根据主键批量查找用户。
     *
     * @param ids 用户主键。
     * @return 用户信息集合。
     */
    List<SysUser> findByIds(final List<Long> ids);

    /**
     * 查询没有部门的用户。
     *
     * @param deptId 部门主键。
     * @return 没有部门的用户（但把当前部门的用户查询出来）。
     */
    List<SysUser> findNoDepartmentUsers(final Long deptId);

    /**
     * 查询全部用户。
     *
     * @return 用户信息集合。
     */
    List<SysUser> findAll();

    /**
     * 根据条件分页查询用户。
     *
     * @param page    分页信息。
     * @param sysUser 查询条件。
     * @return 分页后的用户信息集合。
     */
    Page<SysUser> findByPageAndCondition(final Page<SysUser> page, final SysUser sysUser);

    /**
     * 根据邮箱查询用户主键。
     *
     * @param email 邮箱。
     * @return 用户主键。
     */
    Long findIdByEmail(final String email);

    /**
     * 根据电话号查询用户主键。
     *
     * @param phone 电话号。
     * @return 用户主键。
     */
    Long findIdByPhone(final String phone);

    /**
     * 根据邮箱查询用户数量。
     *
     * @param email 邮箱。
     * @return 用户数量。
     */
    long countByEmail(final String email);

    /**
     * 根据电话号查询用户数量。
     *
     * @param phone 电话号。
     * @return 用户数量。
     */
    long countByPhone(final String phone);

}