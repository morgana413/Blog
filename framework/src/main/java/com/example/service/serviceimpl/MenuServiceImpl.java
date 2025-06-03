package com.example.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.entity.Menu;
import com.example.mapper.MenuMapper;
import com.example.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2025-05-30 08:46:59
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有权限
        if(id == 1){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(long userId) {
        List<Menu> menus;
        if (userId == 1){
            menus = getBaseMapper().selectAllRouterMenu();
        }else {
            menus = getBaseMapper().selectRouterMenuTreeByUserId(userId);
        }
        List<Menu> menuTree = buildMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public List<Menu> selectMenuList(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Menu::getMenuName, menu.getMenuName());
        queryWrapper.like(Menu::getStatus, menu.getStatus());
        queryWrapper.orderByAsc(Menu::getId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        return menus;
    }

    @Override
    public boolean hasChildren(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        List<Menu> menus = list(queryWrapper);
        return !menus.isEmpty();
    }

    @Override
    public List<Long> selectRouterMenuTreeByRoleId(Long id) {
        return getBaseMapper().selectMenuListByRoleId(id);
    }

    private List<Menu> buildMenuTree(List<Menu> menus, long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId() == parentId)
                .map(menu -> menu.setChildren(getChildren(menu,menus)))
                .collect(Collectors.toList());
        //先找出第一层菜单 然后找出他们的子菜设置到children属性中
        return menuTree;
    }

    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(menuItem -> menuItem.getParentId().equals(menu.getId()))
                .collect(Collectors.toList());
        return childrenList;
    }
}
