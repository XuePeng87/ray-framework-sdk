package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysButton;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysButtonMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysButtonService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统按钮的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-07-26
 */
@Service
public class SysButtonServiceImpl extends ServiceImpl<SysButtonMapper, SysButton> implements SysButtonService {

    /**
     * 创建按钮。
     *
     * @param sysButton 按钮实体类。
     * @return 是否创建成功。
     */
    @Override
    public boolean create(final SysButton sysButton) {
        sysButton.setDeleted(Boolean.FALSE);
        sysButton.setCreateTime(LocalDateTime.now());
        sysButton.setModifyTime(LocalDateTime.now());
        return super.save(sysButton);
    }

    /**
     * 修改按钮。
     *
     * @param sysButton 按钮实体类。
     * @return 是否修改成功。
     */
    @Override
    public boolean update(final SysButton sysButton) {
        sysButton.setModifyTime(LocalDateTime.now());
        return super.updateById(sysButton);
    }

    /**
     * 根据主键删除按钮。
     *
     * @param id 按钮主键。
     * @return 是否删除成功。
     */
    @Override
    public boolean deleteById(final Long id) {
        final SysButton sysButton = new SysButton();
        sysButton.setId(id);
        sysButton.setDeleted(Boolean.TRUE);
        sysButton.setModifyTime(LocalDateTime.now());
        return this.update(sysButton);
    }

    /**
     * @return 获取全部按钮。
     */
    @Override
    public List<SysButton> findAll() {
        return super.list();
    }

    /**
     * 根据菜单主键查找按钮。
     *
     * @param menuId 菜单主键。
     * @return 按钮集合。
     */
    @Override
    public List<SysButton> findByMenuId(final Long menuId) {
        final QueryWrapper<SysButton> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysButton::getMenuId, menuId)
                .eq(SysButton::getDeleted, Boolean.FALSE);
        return super.list(wrapper);
    }

    /**
     * 根据菜单主键查询按钮的数量。
     *
     * @param menuId 菜单主键。
     * @return 按钮的数量。
     */
    @Override
    public long findCountByMenuId(final Long menuId) {
        final QueryWrapper<SysButton> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysButton::getMenuId, menuId)
                .eq(SysButton::getDeleted, Boolean.FALSE);
        return super.count(wrapper);
    }

    /**
     * 根据主键批量获取按钮。
     *
     * @param ids 按钮主键集合。
     * @return 按钮集合。
     */
    @Override
    public List<SysButton> findByIds(final List<Long> ids) {
        final QueryWrapper<SysButton> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .in(SysButton::getId, ids)
                .eq(SysButton::getDeleted, Boolean.FALSE);
        return super.list(wrapper);
    }

}