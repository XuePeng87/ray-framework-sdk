package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysButton;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统按钮的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
public interface SysButtonService extends IService<SysButton> {

    /**
     * 创建按钮。
     *
     * @param sysButton 按钮实体类。
     * @return 是否创建成功。
     */
    boolean create(final SysButton sysButton);

    /**
     * 修改按钮。
     *
     * @param sysButton 按钮实体类。
     * @return 是否修改成功。
     */
    boolean update(final SysButton sysButton);

    /**
     * 根据主键删除按钮。
     *
     * @param id 按钮主键。
     * @return 是否删除成功。
     */
    boolean deleteById(final Long id);

    /**
     * @return 获取全部按钮。
     */
    List<SysButton> findAll();

    /**
     * 根据菜单主键查找按钮。
     *
     * @param menuId 菜单主键。
     * @return 按钮集合。
     */
    List<SysButton> findByMenuId(final Long menuId);

    /**
     * 根据菜单主键查询按钮的数量。
     *
     * @param menuId 菜单主键。
     * @return 按钮的数量。
     */
    long findCountByMenuId(final Long menuId);

    /**
     * 根据主键批量获取按钮。
     *
     * @param ids 按钮主键集合。
     * @return 按钮集合。
     */
    List<SysButton> findByIds(final List<Long> ids);

}