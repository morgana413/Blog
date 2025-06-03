package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2025-05-28 18:33:30
 */
public interface UserService extends IService<User> {
    public ResponseResult userInfo();

    ResponseResult  updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult selectUserPage(Integer pageSize, Integer pageNum, User user);

    boolean checkUserNameUnique(String userName);

    boolean checkUserPhoneNumUnique(String phonenumber);

    boolean checkUserMailUnique(String email);

    ResponseResult addUser(User user);

    void updateUser(User user);
}
