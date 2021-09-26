package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-03-01
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 创建部门。
     *
     * @param sysDept sysDept实体类。
     * @return 是否创建成功。
     */
    boolean create(final SysDept sysDept);

    /**
     * 修改部门。
     *
     * @param sysDept sysDept实体类。
     * @return 是否修改成功。
     */
    boolean update(final SysDept sysDept);

    /**
     * 根据主键删除部门。
     *
     * @param id 部门主键。
     * @return 是否删除成功。
     */
    boolean deleteById(final Long id);

    /**
     * 查询全部部门。
     *
     * @return 部门信息集合。
     */
    List<SysDept> findAll();

    /**
     * 根绝主键查询部门。
     *
     * @param ids 主键集合。
     * @return 部门集合。
     */
    List<SysDept> findByIds(final List<Long> ids);

    /**
     * 根据主键查找部门下的用户。
     *
     * @param id 主键。
     * @return 部门下的用户。
     */
    List<Long> findDeptUsersById(final Long id);

    /**
     * 保存部门下的用户。
     *
     * @param id      部门主键。
     * @param userIds 用户主键集合。
     * @return 是否保存成功。
     */
    boolean saveDeptUser(final Long id, final List<Long> userIds);

}