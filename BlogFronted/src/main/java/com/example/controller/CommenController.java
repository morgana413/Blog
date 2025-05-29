package com.example.controller;

import com.example.domain.entity.ResponseResult;
import com.example.service.SgCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommenController {

    @Autowired
    private SgCommentService sgCommentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return sgCommentService.commentList(articleId,pageNum,pageSize);
    }
}
