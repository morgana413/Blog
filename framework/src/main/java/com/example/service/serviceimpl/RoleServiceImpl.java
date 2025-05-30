package com.example.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.Role;
import com.example.domain.vo.PageVo;
import com.example.mapper.RoleMapper;
import com.example.service.RoleService;
import com.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2025-05-30 08:57:34
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //如果是管理员，返回admin
        if(id == 1){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询对应角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, Role role) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(role.getRoleName()),Role::getRoleName,role.getRoleName());
        queryWrapper.eq(StringUtils.hasText(role.getStatus()),Role::getStatus,role.getStatus());
        queryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);
        List<Role> roles = page.getRecords();

        PageVo pageVo = new PageVo(roles,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}
