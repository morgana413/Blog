package com.example.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.User;
import com.example.domain.entity.UserRole;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.UserInfoVo;
import com.example.domain.vo.UserVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2025-05-28 18:33:31
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleServiceImpl userRoleService;

    @Override
    public ResponseResult userInfo() {
        //从token获取用户id
        Long userId = SecurityUtils.getUserId();
        //获取用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(User user) {

        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_IS_NULL);
        }

        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_IS_NULL);
        }

        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_IS_NULL);
        }

        if (!StringUtils.hasText(user.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.MAIL_IS_NULL);
        }
        //验证数据是否唯一
        if (userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }

        if (userMailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        if (userNicknamexist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //对密码进行加密
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectUserPage(Integer pageSize, Integer pageNum, User user) {
        Page<User> page = new Page<>(pageSize,pageNum);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.like(StringUtils.hasText(user.getUserName()),User::getUserName,user.getUserName());
        wrapper.eq(StringUtils.hasText(user.getPhonenumber()),User::getPhonenumber,user.getPhonenumber());
        wrapper.eq(StringUtils.hasText(user.getStatus() ),User::getStatus,user.getStatus());
        page(page,wrapper);
        List<User> userList = page.getRecords();
        List<UserVo> userVos = userList.stream()
                .map(u -> BeanCopyUtils.copyBean(u, UserVo.class))
                .collect(Collectors.toList());
        PageVo pageVo =new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(userVos);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public boolean checkUserNameUnique(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,userName);
        return count(wrapper) == 0;
    }

    @Override
    public boolean checkUserPhoneNumUnique(String phonenumber) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhonenumber,phonenumber);
        return count(wrapper) == 0;
    }

    @Override
    public boolean checkUserMailUnique(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail,email);
        return count(wrapper) == 0;
    }

    @Override
    public ResponseResult addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
        if (user.getRoleIds()!=null&&user.getRoleIds().length>0){
            insertUserRole(user);
        }
        return ResponseResult.okResult();
    }

    @Override
    public void updateUser(User user) {
    LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(UserRole::getUserId,user.getId());
    userRoleService.remove(wrapper);
    insertUserRole(user);
    updateById(user);
    }

    private void insertUserRole(User user) {
        List<UserRole> userRoles = Arrays.stream(user.getRoleIds())
                .map(roleId-> new UserRole(user.getId(),roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(userRoles);
    }

    private boolean userNicknamexist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper) > 0;
    }

    private boolean userMailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }
}
