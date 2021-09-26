package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysDict;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * SysDict的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-27
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 创建SysDict。
     *
     * @param sysDict SysDict实体类。
     * @return 是否创建成功。
     */
    boolean create(final SysDict sysDict);

    /**
     * 修改SysDict。
     *
     * @param sysDict SysDict实体类。
     * @return 是否修改成功。
     */
    boolean update(final SysDict sysDict);

    /**
     * 根据主键删除SysDict。
     *
     * @param id SysDict主键。
     * @return 是否删除成功。
     */
    boolean deleteById(final Long id);

    /**
     * 根据主键查找SysDict。
     *
     * @param id SysDict主键。
     * @return SysDict信息。
     */
    SysDict findById(final Long id);

    /**
     * 根据类型主键查找SysDict。
     *
     * @param typeId 类型主键。
     * @return SysDict集合。
     */
    List<SysDict> findByTypeId(final Long typeId);

    /**
     * 根据条件分页查询SysDict。
     *
     * @param page    分页信息。
     * @param sysDict 查询条件。
     * @return 分页后的SysDict信息集合。
     */
    Page<SysDict> findByPageAndCondition(final Page<SysDict> page, final SysDict sysDict);

}