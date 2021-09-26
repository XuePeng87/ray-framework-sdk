package cc.xuepeng.ray.framework.sdk.mgt.mapper;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-26
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询没有部门的用户。
     *
     * @param deptId 部门主键。
     * @return 没有部门的用户（但把当前部门的用户查询出来）。
     */
    List<SysUser> findNoDepartmentUsers(@Param("deptId") final Long deptId);

}
