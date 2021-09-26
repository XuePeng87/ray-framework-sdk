package cc.xuepeng.ray.framework.sdk.mgt.service;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * SysDictType的服务类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-27
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 创建SysDictType。
     *
     * @param sysDictType SysDictType实体类。
     * @return 是否创建成功。
     */
    boolean create(final SysDictType sysDictType);

    /**
     * 修改SysDictType。
     *
     * @param sysDictType SysDictType实体类。
     * @return 是否修改成功。
     */
    boolean update(final SysDictType sysDictType);

    /**
     * 根据主键删除SysDictType。
     *
     * @param id SysDictType主键。
     * @return 是否删除成功。
     */
    boolean deleteById(final Long id);

    /**
     * @return 查询全部字典类型。
     */
    List<SysDictType> findAll();

}