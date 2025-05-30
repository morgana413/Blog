package com.controller;

import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.Role;
import com.example.domain.vo.PageVo;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    private ResponseResult roleList(Integer pageNum, Integer pageSize, Role role) {
        return roleService.roleList(pageNum,pageSize,role);
    }
}
