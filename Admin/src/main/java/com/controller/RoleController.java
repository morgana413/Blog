package com.controller;

import com.example.domain.dto.ChangeRoleStatusDto;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.Role;
import com.example.domain.vo.PageVo;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    private ResponseResult roleList(Integer pageNum, Integer pageSize, Role role) {
        return roleService.roleList(pageNum,pageSize,role);
    }

    @PutMapping("/changeStatus")
    private ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto) {
        Role role = new Role();
        role.setStatus(changeRoleStatusDto.getStatus());
        role.setId(changeRoleStatusDto.getRoleId());
        roleService.updateById(role);
        return ResponseResult.okResult();
    }
    @PostMapping
    private ResponseResult add(@RequestBody Role role) {
    roleService.insertRole(role);
    return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    private ResponseResult getInfo(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return ResponseResult.okResult(role);
    }

    @PutMapping
    private ResponseResult update(@RequestBody Role role) {
        roleService.updateById(role);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    private ResponseResult delete(@PathVariable Long id) {
        roleService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/listAllRole")
    private ResponseResult listAllRole(){
        List<Role> roles = roleService.selectRoleAll();
        return ResponseResult.okResult(roles);
    }
}
