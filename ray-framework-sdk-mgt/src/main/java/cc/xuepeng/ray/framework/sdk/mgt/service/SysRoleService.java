package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.Function;
import cc.xuepeng.ray.framework.sdk.mgt.entity.SysRole;
import cc.xuepeng.ray.framework.sdk.mgt.entity.SysRoleFunctions;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 创建角色。
     *
     * @param sysRole 角色实体类。
     * @return 是否创建成功。
     */
    boolean create(final SysRole sysRole);

    /**
     * 修改角色。
     *
     * @param sysRole 角色实体类。
     * @return 是否修改成功。
     */
    boolean update(final SysRole sysRole);

    /**
     * 保存角色下的用户。
     *
     * @param id      角色主键。
     * @param userIds 用户主键集合。
     * @return 是否保存成功。
     */
    boolean saveRoleUser(final Long id, final List<Long> userIds);

    /**
     * 保存角色下的功能。
     *
     * @param id        角色主键。
     * @param functions 功能集合。
     * @return 是否保存成功。
     */
    boolean saveRoleFunction(final Long id, final List<Function> functions);

    /**
     * 根据主键删除角色。
     *
     * @param id 角色主键。
     * @return 是否删除成功。
     */
    boolean deleteById(final Long id);

    /**
     * 根据主键批量删除角色。
     *
     * @param ids 角色主键集合。
     * @return 是否删除成功。
     */
    boolean deleteByIds(final List<Long> ids);

    /**
     * @return 获取全部角色。
     */
    List<SysRole> findAll();

    /**
     * 根据角色主键批量查询角色。
     *
     * @param ids 角色主键集合。
     * @return 角色信息集合。
     */
    List<SysRole> findByIds(final List<Long> ids);

    /**
     * 根据条件分页查询用户。
     *
     * @param page    分页信息。
     * @param sysRole 查询条件。
     * @return 分页后的角色信息集合。
     */
    Page<SysRole> findByPageAndCondition(final Page<SysRole> page, final SysRole sysRole);

    /**
     * 根据主键查询角色下的用户。
     *
     * @param id 主键。
     * @return 角色下的用户。
     */
    List<Long> findRoleUsersById(final Long id);

    /**
     * 根据主键查询角色下的功能。
     *
     * @param id 主键。
     * @return 角色下的功能。
     */
    SysRoleFunctions findRoleFunctionsById(final Long id);

    /**
     * 保存角色与API的鉴权关系。
     *
     * @param id   角色主键。
     * @param urls API的路径。
     * @return 是否保存成功。
     */
    boolean saveRoleApi(final Long id, final List<String> urls);

    /**
     * 根据主键查询角色下的API路径。
     *
     * @param id 角色主键。
     * @return 角色下的API路径。
     */
    List<String> findRoleApiById(final Long id);

    /**
     * @return 查询全部API信息。
     */
    List<String> findAllRoleApi();

    /**
     * 根据API查询角色编号。
     *
     * @param api API路径。
     * @return 角色编号集合。
     */
    List<String> findRoleCodeByRoleApi(final String api);

    /**
     * 查询要创建的角色编号是否已经存在。
     *
     * @param id   角色主键。
     * @param code 角色编号。
     * @return 是否已存在。
     */
    boolean codeExists(final Long id, final String code);

    /**
     * 查询要创建的角色名称是否已经存在。
     *
     * @param id   角色主键。
     * @param name 角色名称。
     * @return 是否已存在。
     */
    boolean nameExists(final Long id, final String name);

}