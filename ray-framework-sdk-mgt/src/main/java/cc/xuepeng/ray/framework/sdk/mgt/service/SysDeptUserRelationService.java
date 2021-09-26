package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysDeptUserRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门与用户关系的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-03-01
 */
public interface SysDeptUserRelationService extends IService<SysDeptUserRelation> {

    /**
     * 根据部门主键删除部门与人员的关系。
     *
     * @param deptId 部门主键。
     * @return 是否删除成功。
     */
    boolean deleteByDeptId(final Long deptId);

    /**
     * 根据部门主键批量删除部门与人员的关系。
     *
     * @param deptIds 部门主键集合。
     * @return 是否删除成功。
     */
    boolean deleteByDeptIds(final List<Long> deptIds);

    /**
     * 根据用户主键删除部门与人员的关系。
     *
     * @param userId 用户主键。
     * @return 是否删除成功。
     */
    boolean deleteByUserId(final Long userId);

    /**
     * 根据用户主键批量删除部门与人员的关系。
     *
     * @param userIds 用户主键。
     * @return 是否删除成功。
     */
    boolean deleteByUserIds(final List<Long> userIds);

    /**
     * 根据用户主键批量删除部门与人员的关系。
     *
     * @param userId 用户主键。
     * @return 是否删除成功。
     */
    List<Long> findDeptIdsByUserId(final Long userId);

}