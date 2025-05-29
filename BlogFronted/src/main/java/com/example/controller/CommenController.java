package com.example.controller;

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
        return sgCommentService.commentList(articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody SgComment sgComment) {
        return  sgCommentService.addComment(sgComment);
    }
}
