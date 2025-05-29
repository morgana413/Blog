package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entity.SgComment;
import org.apache.ibatis.annotations.Mapper;


/**
 * 评论表(SgComment)表数据库访问层
 *
 * @author makejava
 * @since 2025-05-28 17:51:37
 */
@Mapper
public interface SgCommentMapper extends BaseMapper<SgComment> {

}

