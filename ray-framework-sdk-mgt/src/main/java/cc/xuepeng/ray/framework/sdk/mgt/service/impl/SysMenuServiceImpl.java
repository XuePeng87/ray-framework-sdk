package cc.xuepeng.ray.framework.sdk.mgt.service.impl;

import cc.xuepeng.ray.framework.sdk.mgt.entity.SysButton;
import cc.xuepeng.ray.framework.sdk.mgt.entity.SysMenu;
import cc.xuepeng.ray.framework.sdk.mgt.entity.SysRoleFunctionRelation;
import cc.xuepeng.ray.framework.sdk.mgt.enums.FunctionType;
import cc.xuepeng.ray.framework.sdk.mgt.exception.SysMenuCannotBeDeletedException;
import cc.xuepeng.ray.framework.sdk.mgt.mapper.SysMenuMapper;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysButtonService;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysMenuService;
import cc.xuepeng.ray.framework.sdk.mgt.service.SysRoleFunctionRelationService;
import cc.xuepeng.ray.framework.sdk.mgt.service.impl.formatter.SysMenuLevelFormatter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单的服务实现类。
 * </p>
 *
 * @author xuepeng
 * @since 2021-01-26
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    /**
     * 创建菜单。
     *
     * @param sysMenu 菜单实体类。
     * @return 是否创建成功。
     */
    @Override
    public boolean create(final SysMenu sysMenu) {
        sysMenu.setDeleted(Boolean.FALSE);
        sysMenu.setCreateTime(LocalDateTime.now());
        sysMenu.setModifyTime(LocalDateTime.now());
        return super.save(sysMenu);
    }

    /**
     * 修改菜单。
     *
     * @param sysMenu 菜单实体类。
     * @return 是否修改成功。
     */
    @Override
    public boolean update(final SysMenu sysMenu) {
        sysMenu.setModifyTime(LocalDateTime.now());
        return super.updateById(sysMenu);
    }

    /**
     * 根据主键删除菜单。
     * 菜单下有按钮将不能删除，否则会抛出SysMenuCannotBeDeletedException。
     *
     * @param id 菜单主键。
     * @return 是否删除成功。
     */
    @Override
    @Transactional
    public boolean deleteById(final Long id) {
        if (hasChildren(id)) {
            throw new SysMenuCannotBeDeletedException("菜单不能删除，请先删除子菜单。");
        }
        // 菜单下还有按钮不能删除
        if (sysButtonService.findCountByMenuId(id) > 0) {
            throw new SysMenuCannotBeDeletedException("菜单不能删除，请先删除菜单下的按钮。");
        }
        // 删除菜单与角色的关系
        sysRoleFunctionRelationService.deleteByFunctionId(id);
        // 删除菜单
        final SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        sysMenu.setDeleted(Boolean.TRUE);
        sysMenu.setModifyTime(LocalDateTime.now());
        return this.update(sysMenu);
    }

    /**
     * @return 查询全部菜单。
     */
    @Override
    public List<SysMenu> findAll() {
        final QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysMenu::getDeleted, Boolean.FALSE)
                .eq(SysMenu::getFixed, Boolean.FALSE)
                .orderByAsc(SysMenu::getMenuSort);
        final List<SysMenu> sysMenus = super.list(wrapper);
        final List<SysButton> sysButtons = sysButtonService.findAll();
        assemblyMenuAndButton(sysMenus, sysButtons);
        return sysMenuLevelFormatter.format(sysMenus);
    }

    /**
     * @return 查询一级菜单。
     */
    @Override
    public Set<Long> findTopMenuIds() {
        final QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysMenu::getMenuLevel, 1)
                .eq(SysMenu::getDeleted, Boolean.FALSE)
                .eq(SysMenu::getFixed, Boolean.FALSE)
                .orderByAsc(SysMenu::getMenuSort);
        return super.list(wrapper)
                .stream()
                .map(SysMenu::getId)
                .collect(Collectors.toSet());
    }

    /**
     * 根据角色主键查询菜单。
     *
     * @param roleIds 角色主键集合。
     * @return 菜单集合。
     */
    @Override
    public List<SysMenu> findByRoleIds(final List<Long> roleIds) {
        // 根据角色主键查询这些角色所拥有的菜单
        final Map<String, Set<Long>> functions = findFunctionIdsByRoleIds(roleIds);
        final List<Long> menuIds = new ArrayList<>(functions.get("menus"));
        final List<Long> buttonIds = new ArrayList<>(functions.get("buttons"));
        if (CollectionUtils.isEmpty(menuIds)) {
            return new ArrayList<>();
        }
        // 根据角色主键查询菜单
        return findByMenuIdsAndButtonIds(menuIds, buttonIds);
    }

    /**
     * 判断是否存在子菜单。
     *
     * @param id 菜单主键。
     * @return 是否存在子菜单。
     */
    @Override
    public boolean hasChildren(final Long id) {
        final QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysMenu::getMenuPid, id)
                .eq(SysMenu::getDeleted, Boolean.FALSE);
        return super.count(wrapper) > 0;
    }

    /**
     * 根据角色主键查询角色所拥有的功能主键。
     *
     * @param roleIds 角色主键集合。
     * @return 菜单主键Map，Map中有两个Key，分别是menus和buttons。
     */
    private Map<String, Set<Long>> findFunctionIdsByRoleIds(final List<Long> roleIds) {
        final List<SysRoleFunctionRelation> sysRoleFunctionRelations = sysRoleFunctionRelationService.findByRoleIds(roleIds);
        final Set<Long> menuIds = new HashSet<>();
        final Set<Long> buttonIds = new HashSet<>();
        for (SysRoleFunctionRelation sysRoleFunctionRelation : sysRoleFunctionRelations) {
            if (sysRoleFunctionRelation.getFunctionType() == FunctionType.MENU.ordinal()) {
                menuIds.add(sysRoleFunctionRelation.getFunctionId());
            } else {
                buttonIds.add(sysRoleFunctionRelation.getFunctionId());
            }
        }
        final Map<String, Set<Long>> result = new HashMap<>();
        result.put("menus", menuIds);
        result.put("buttons", buttonIds);
        return result;
    }

    /**
     * 根据菜单主键查询菜单信息。
     * 查询菜单信息后，在查询菜单下的按钮。
     *
     * @param menuIds   菜单主键集合。
     * @param buttonIds 按钮主键集合。
     * @return 菜单信息集合。
     */
    private List<SysMenu> findByMenuIdsAndButtonIds(final List<Long> menuIds, final List<Long> buttonIds) {
        // 获取菜单并设置层级
        final QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(SysMenu::getDeleted, Boolean.FALSE)
                .in(SysMenu::getId, menuIds)
                .orderByAsc(SysMenu::getMenuSort);
        final List<SysMenu> sysMenus = sysMenuLevelFormatter.format(super.list(wrapper));
        // 获取按钮
        if (CollectionUtils.isNotEmpty(buttonIds)) {
            List<SysButton> sysButtons = sysButtonService.findByIds(buttonIds);
            // 装配菜单和按钮，将按钮添加到菜单下
            assemblyMenuAndButton(sysMenus, sysButtons);
        }
        return sysMenus;
    }

    /**
     * 装配菜单和按钮，将按钮添加到菜单下。
     *
     * @param sysMenus   按钮集合。
     * @param sysButtons 菜单集合。
     */
    private void assemblyMenuAndButton(List<SysMenu> sysMenus, List<SysButton> sysButtons) {
        final Map<Long, List<SysButton>> buttonMap = new HashMap<>();
        for (SysButton sysButton : sysButtons) {
            if (buttonMap.containsKey(sysButton.getMenuId())) {
                buttonMap.get(sysButton.getMenuId()).add(sysButton);
            } else {
                final List<SysButton> btnList = new ArrayList<>();
                btnList.add(sysButton);
                buttonMap.put(sysButton.getMenuId(), btnList);
            }
        }
        for (SysMenu sysMenu : sysMenus) {
            if (CollectionUtils.isNotEmpty(sysMenu.getChildren())) {
                assemblyMenuAndButton(sysMenu.getChildren(), sysButtons);
            } else {
                List<SysButton> btns = buttonMap.get(sysMenu.getId());
                sysMenu.setButtons(CollectionUtils.isNotEmpty(btns) ? btns : new ArrayList<>());
            }
        }
    }

    /**
     * 自动装配角色与功能项关系的业务处理接口。
     *
     * @param sysRoleFunctionRelationService 角色与功能项关系的业务处理接口。
     */
    @Autowired
    public void setSysRoleFunctionRelationService(SysRoleFunctionRelationService sysRoleFunctionRelationService) {
        this.sysRoleFunctionRelationService = sysRoleFunctionRelationService;
    }

    /**
     * 自动装配按钮的业务处理接口。
     *
     * @param sysButtonService 按钮的业务处理接口。
     */
    @Autowired
    public void setSysButtonService(SysButtonService sysButtonService) {
        this.sysButtonService = sysButtonService;
    }

    /**
     * 自动装配菜单层级转换器。
     *
     * @param sysMenuLevelFormatter 菜单层级转换器。
     */
    @Autowired
    public void setSysMenuLevelFormatter(SysMenuLevelFormatter sysMenuLevelFormatter) {
        this.sysMenuLevelFormatter = sysMenuLevelFormatter;
    }

    /**
     * 角色与功能项关系的业务处理接口。
     */
    private SysRoleFunctionRelationService sysRoleFunctionRelationService;

    /**
     * Button的业务处理接口。
     */
    private SysButtonService sysButtonService;

    /**
     * 菜单层级转换器。
     */
    private SysMenuLevelFormatter sysMenuLevelFormatter;

}