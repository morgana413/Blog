package com.controller;

import com.example.annotation.SystemLog;
import com.example.domain.entity.LoginUser;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.User;
import com.example.domain.vo.AdminUserInfoVo;
import com.example.domain.vo.UserInfoVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.service.LoginService;
import com.example.service.MenuService;
import com.example.service.RoleService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.SecurityUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private LoginService LoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @SystemLog(businessName = "后台登录")
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!Strings.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return LoginService.login(user);
    }

    @GetMapping("getInfo")
    public ResponseResult getInfo(){
        //获取当前用户信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户ID获取用户权限
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户ID查询角色信息
        List<String> roleKeyByUserId= roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //封装数据返回
        UserInfoVo user = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyByUserId,user);
        return ResponseResult.okResult(adminUserInfoVo);
    }

}
