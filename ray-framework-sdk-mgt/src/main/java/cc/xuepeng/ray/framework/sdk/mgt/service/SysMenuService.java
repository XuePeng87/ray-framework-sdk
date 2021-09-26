package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 创建菜单。
     *
     * @param menu 菜单实体类。
     * @return 是否创建成功。
     */
    boolean create(final SysMenu menu);

    /**
     * 修改菜单。
     *
     * @param menu 菜单实体类。
     * @return 是否修改成功。
     */
    boolean update(final SysMenu menu);

    /**
     * 根据主键删除菜单。
     *
     * @param id 菜单主键。
     * @return 是否删除成功。
     */
    boolean deleteById(final Long id);

    /**
     * @return 查询全部菜单。
     */
    List<SysMenu> findAll();

    /**
     * @return 查询一级菜单的主键。
     */
    Set<Long> findTopMenuIds();

    /**
     * 根据角色主键查询菜单。
     *
     * @param roleIds 角色主键集合。
     * @return 菜单集合。
     */
    List<SysMenu> findByRoleIds(final List<Long> roleIds);

    /**
     * 判断是否存在子菜单。
     *
     * @param id 菜单主键。
     * @return 是否存在子菜单。
     */
    boolean hasChildren(final Long id);

}