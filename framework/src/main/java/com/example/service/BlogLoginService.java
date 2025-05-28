package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.User;

public interface BlogLoginService{
    ResponseResult login(User user);
}
