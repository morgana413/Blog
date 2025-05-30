package com.controller;

import com.example.domain.entity.Menu;
import com.example.domain.entity.ResponseResult;
import com.example.domain.vo.MenuVo;
import com.example.service.MenuService;
import com.example.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
