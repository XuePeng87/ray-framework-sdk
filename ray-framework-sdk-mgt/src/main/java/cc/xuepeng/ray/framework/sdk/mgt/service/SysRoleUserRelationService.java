package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysRoleUserRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与用户关系的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
public interface SysRoleUserRelationService extends IService<SysRoleUserRelation> {

    /**
     * 根据角色主键删除角色与人员的关系。
     *
     * @param roleId 角色主键。
     * @return 是否删除成功。
     */
    boolean deleteByRoleId(final Long roleId);

    /**
     * 根据角色主键批量删除角色与人员的关系。
     *
     * @param roleIds 角色主键集合。
     * @return 是否删除成功。
     */
    boolean deleteByRoleIds(final List<Long> roleIds);

    /**
     * 根据用户主键删除角色与人员的关系。
     *
     * @param userId 用户主键。
     * @return 是否删除成功。
     */
    boolean deleteByUserId(final Long userId);

    /**
     * 根据用户主键批量删除角色与人员的关系。
     *
     * @param userIds 用户主键。
     * @return 是否删除成功。
     */
    boolean deleteByUserIds(final List<Long> userIds);

    /**
     * 根据用户主键查询角色主键集合。
     *
     * @param userId 用户主键。
     * @return 角色主键集合。
     */
    List<Long> findRoleIdsByUserId(final Long userId);

}