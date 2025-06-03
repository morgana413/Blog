package com.controller;

import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.User;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

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
}
