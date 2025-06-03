package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2025-06-03 10:50:05
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

