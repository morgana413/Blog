package com.example.service.serviceimpl;

import com.example.domain.entity.LoginUser;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.User;
import com.example.domain.vo.BlogUserLoginVo;
import com.example.domain.vo.UserInfoVo;
import com.example.service.LoginService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+id,loginUser);
        //封装token
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

}
