package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2025-05-30 08:57:36
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    List<Long> selectRoleIdByUserId(Long id);
}

