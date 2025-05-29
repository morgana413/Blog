package com.example.controller;

import com.example.constants.SystemConstants;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.SgComment;
import com.example.service.SgCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommenController {

    @Autowired
    private SgCommentService sgCommentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return sgCommentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody SgComment sgComment) {
        return  sgCommentService.addComment(sgComment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
       return sgCommentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
