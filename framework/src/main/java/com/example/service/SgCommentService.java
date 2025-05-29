package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.SgComment;


/**
 * 评论表(SgComment)表服务接口
 *
 * @author makejava
 * @since 2025-05-28 17:51:34
 */
public interface SgCommentService extends IService<SgComment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(SgComment sgComment);
}
