package com.example.service;

import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

}
