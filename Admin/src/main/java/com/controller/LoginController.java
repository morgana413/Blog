package com.controller;

import com.example.annotation.SystemLog;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.User;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.service.LoginService;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService LoginService;

    @SystemLog(businessName = "后台登录")
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!Strings.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return LoginService.login(user);
    }

}
