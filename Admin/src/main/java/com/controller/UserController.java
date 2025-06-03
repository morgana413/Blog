package com.controller;

import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult getUserList(Integer pageSize, Integer pageNum, User user) {
        return userService.selectUserPage(pageSize,pageNum,user);
    }
}
