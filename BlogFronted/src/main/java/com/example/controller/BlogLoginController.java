package com.example.controller;

import com.example.annotation.SystemLog;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.User;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.service.BlogLoginService;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @SystemLog(businessName = "登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!Strings.hasText(user.getUserName())){
        throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/loginout")
    public ResponseResult logout(){
        return blogLoginService.loginout();
    }
}
