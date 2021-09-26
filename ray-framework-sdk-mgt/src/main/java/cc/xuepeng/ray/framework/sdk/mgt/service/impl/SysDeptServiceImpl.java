package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysDept;
import cc.xuepeng.ray.framework.sdk.mgt.entity.SysDeptUserRelation;
import cc.xuepeng.ray.framework.sdk.mgt.exception.SysDeptCannotBeDeletedException;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysDeptMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysDeptService;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysDeptUserRelationService;
import cc.xuepeng.ray.framework.sdk.mgt.service.impl.formatter.SysDeptLevelFormatter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-03-01
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    /**
     * 创建部门。
     *
     * @param sysDept 部门实体类。
     * @return 是否创建成功。
     */
    @Override
    public boolean create(final SysDept sysDept) {
        sysDept.setDeleted(Boolean.FALSE);
        sysDept.setCreateTime(LocalDateTime.now());
        sysDept.setModifyTime(LocalDateTime.now());
        return super.save(sysDept);
    }

    /**
     * 修改部门。
     *
     * @param sysDept 部门实体类。
     * @return 是否修改成功。
     */
    @Override
    public boolean update(final SysDept sysDept) {
        sysDept.setModifyTime(LocalDateTime.now());
        return super.updateById(sysDept);
    }

    /**
     * 根据主键删除部门。
     *
     * @param id 部门主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteById(final Long id) {
        if (hasUser(id)) {
            throw new SysDeptCannotBeDeletedException("部门不能删除，请先删除部门下的用户。");
        }
        if (hasDept(id)) {
            throw new SysDeptCannotBeDeletedException("部门不能删除，请先删除子部门。");
        }
        final SysDept sysDept = new SysDept();
        sysDept.setId(id);
        sysDept.setDeleted(Boolean.TRUE);
        sysDept.setModifyTime(LocalDateTime.now());
        return super.updateById(sysDept);
    }

    /**
     * 查询全部部门。
     *
     * @return 部门信息集合。
     */
    @Override
    public List<SysDept> findAll() {
        final QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysDept::getDeleted, Boolean.FALSE)
                .orderByAsc(SysDept::getDeptSort);
        final List<SysDept> sysDepts = super.list(wrapper);
        return sysDeptLevelFormatter.format(sysDepts);
    }

    /**
     * 根绝主键查询部门。
     *
     * @param ids 主键集合。
     * @return 部门集合。
     */
    public List<SysDept> findByIds(final List<Long> ids) {
        final QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(SysDept::getId, ids);
        return super.list(wrapper);
    }

    /**
     * 根据主键查找部门下的用户。
     *
     * @param id 主键。
     * @return 部门下的用户。
     */
    @Override
    public List<Long> findDeptUsersById(final Long id) {
        final QueryWrapper<SysDeptUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysDeptUserRelation::getDeptId, id);
        final List<SysDeptUserRelation> sysDeptUserRelations = sysDeptUserRelationService.list(wrapper);
        final List<Long> userIds = sysDeptUserRelations.stream()
                .map(SysDeptUserRelation::getUserId)
                .distinct()
                .collect(Collectors.toList());
        return Collections.unmodifiableList(userIds);
    }

    /**
     * 保存部门下的用户。
     *
     * @param id      部门主键。
     * @param userIds 用户主键集合。
     * @return 是否保存成功。
     */
    @Override
    @Transactional
    public boolean saveDeptUser(final Long id, final List<Long> userIds) {
        // 删除部门与人员的关系
        sysDeptUserRelationService.deleteByDeptId(id);
        // 创建部门与人员的关系
        final List<SysDeptUserRelation> sysDeptUserRelations = new ArrayList<>();
        for (Long userId : userIds) {
            final SysDeptUserRelation sysDeptUserRelation = new SysDeptUserRelation();
            sysDeptUserRelation.setDeptId(id);
            sysDeptUserRelation.setUserId(userId);
            sysDeptUserRelations.add(sysDeptUserRelation);
        }
        if (CollectionUtils.isEmpty(sysDeptUserRelations)) {
            return true;
        }
        return sysDeptUserRelationService.saveBatch(sysDeptUserRelations);
    }

    /**
     * 判断部门下是否有用户存在。
     *
     * @param id 部门主键。
     * @return 部门下是否有用户存在。
     */
    private boolean hasUser(final Long id) {
        final QueryWrapper<SysDeptUserRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysDeptUserRelation::getDeptId, id);
        return sysDeptUserRelationService.count(wrapper) > 0;
    }


    /**
     * 根据主键查找Department。
     *
     * @param id Department主键。
     * @return Department信息。
     */
    private boolean hasDept(final Long id) {
        final QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysDept::getDeptPid, id)
                .eq(SysDept::getDeleted, Boolean.FALSE);
        return super.count(wrapper) > 0;
    }


    /**
     * 自动装配部门用户关系服务接口。
     *
     * @param sysDeptUserRelationService 部门用户关系服务接口。
     */
    @Autowired
    public void setSysDeptUserRelationService(SysDeptUserRelationService sysDeptUserRelationService) {
        this.sysDeptUserRelationService = sysDeptUserRelationService;
    }

    /**
     * 自动装配部门格式化器。
     *
     * @param sysDeptLevelFormatter 部门格式化器。
     */
    @Autowired
    public void setSysDeptLevelFormatter(SysDeptLevelFormatter sysDeptLevelFormatter) {
        this.sysDeptLevelFormatter = sysDeptLevelFormatter;
    }

    /**
     * 部门用户关系服务接口。
     */
    private SysDeptUserRelationService sysDeptUserRelationService;

    /**
     * 部门格式化器。
     */
    private SysDeptLevelFormatter sysDeptLevelFormatter;

}