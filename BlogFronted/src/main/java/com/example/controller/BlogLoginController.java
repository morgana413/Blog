package com.example.controller;

import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.User;
import com.example.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;


    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){

        return blogLoginService.login(user);
    }
}
