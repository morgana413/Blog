package com.controller;

import com.example.domain.entity.Menu;
import com.example.domain.entity.ResponseResult;
import com.example.domain.vo.MenuTreeVo;
import com.example.domain.vo.MenuVo;
import com.example.domain.vo.RoleMenuSelectVo;
import com.example.service.MenuService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuControll {

    @Autowired
    MenuService menuService;

    @GetMapping("/list")
    public ResponseResult listMenu(Menu menu){
        List<Menu> menuList = menuService.selectMenuList(menu);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menuList, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @PostMapping("/add")
    public ResponseResult addMenu(Menu menu){
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult updateMenu(@PathVariable("id") Menu menu){
        if (menu.getId().equals(menu.getParentId())){
        return ResponseResult.errorResult(500,"修改菜单"+menu.getMenuName()+"失败，上级菜单不能选择自己");
        }
        menuService.updateById(menu);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId){
        if (menuService.hasChildren(menuId)){
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        menuService.removeById(menuId);
        return ResponseResult.okResult();
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("id") Long id){
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<Long> checkedKeys =menuService.selectRouterMenuTreeByRoleId(id);
        List<MenuTreeVo>menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuSelectVo roleMenuSelectVo = new RoleMenuSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(roleMenuSelectVo);
    }

}
