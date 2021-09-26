package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysRoleApiRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色与API关系的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
public interface SysRoleApiRelationService extends IService<SysRoleApiRelation> {

    /**
     * 根据角色主键删除角色与API的关系。
     *
     * @param roleId 角色主键。
     * @return 是否删除成功。
     */
    boolean deleteByRoleId(final Long roleId);

    /**
     * @return 查询全部的Api信息。
     */
    List<String> findAllApis();

    /**
     * 根据API查询角色主键集合。
     *
     * @param api API地址。
     * @return 角色主键集合。
     */
    List<Long> findRoleIdsByApi(final String api);

    /**
     * 根据角色主键查询API信息。
     *
     * @param roleId 角色主键。
     * @return API信息。
     */
    List<String> findApisByRoleId(final Long roleId);

}