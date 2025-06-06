package com.controller;

import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.Role;
import com.example.domain.entity.User;
import com.example.domain.vo.UserInfoAndRoleIdsVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.service.RoleService;
import com.example.service.UserService;
import com.example.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @GetMapping("/list")
    public ResponseResult getUserList(Integer pageSize, Integer pageNum, User user) {
        return userService.selectUserPage(pageSize,pageNum,user);
    }

    @PostMapping
    private ResponseResult insertUser(@RequestBody User user) {
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_IS_NULL);
        }
        if(!userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!userService.checkUserPhoneNumUnique(user.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (!userService.checkUserMailUnique(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        return userService.addUser(user);
    }

    @DeleteMapping("/{ids}")
    private ResponseResult deleteUser(@PathVariable List<Long> ids) {
        if(ids.contains(SecurityUtils.getUserId())){
            return ResponseResult.errorResult(500,"不能删除当前登录角色");
        }
        userService.removeByIds(ids);
        return ResponseResult.okResult();
    }
    @GetMapping("/{id}")
    public ResponseResult getUserInfoById(@PathVariable Long id) {
    List<Role> roles = roleService.selectRoleAll();
    User user = userService.getById(id);
    List<Long> roleIds = roleService.selectRoleIdByUserId(id);
        UserInfoAndRoleIdsVo userInfoAndRoleIdsVo = new UserInfoAndRoleIdsVo(user,roles,roleIds);
        return ResponseResult.okResult(userInfoAndRoleIdsVo);
    }

    @PutMapping
    private ResponseResult updateUser(@RequestBody User user) {
    userService.updateUser(user);
    return ResponseResult.okResult();
    }
}