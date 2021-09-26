package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysRoleFunctionRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与功能关系的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
public interface SysRoleFunctionRelationService extends IService<SysRoleFunctionRelation> {

    /**
     * 根据角色主键删除角色与功能项的关系。
     *
     * @param roleId 角色主键。
     * @return 是否删除成功。
     */
    boolean deleteByRoleId(final Long roleId);

    /**
     * 根据角色主键批量删除角色与功能项的关系。
     *
     * @param roleIds 角色主键集合。
     * @return 是否删除成功。
     */
    boolean deleteByRoleIds(final List<Long> roleIds);

    /**
     * 根据功能项主键删除角色与功能项的关系。
     *
     * @param functionId 角色主键。
     * @return 是否删除成功。
     */
    boolean deleteByFunctionId(final Long functionId);

    /**
     * 根据角色主键查询角色与功能项的关系。
     *
     * @param roleIds 角色主键集合。
     * @return 角色与功能项的关系。
     */
    List<SysRoleFunctionRelation> findByRoleIds(final List<Long> roleIds);

}