package com.example.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author makejava
 * @since 2025-06-03 10:50:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class UserRole  {
//用户ID@TableId
    private Long userId;
//角色ID@TableId
    private Long roleId;

}
