package com.example.controller;

import com.example.constants.SystemConstants;
import com.example.domain.dto.AddCommentDto;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.SgComment;
import com.example.service.SgCommentService;
import com.example.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(description = "评论相关接口")
public class CommenController {

    @Autowired
    private SgCommentService sgCommentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        return sgCommentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody AddCommentDto comment) {
        SgComment sgComment = BeanCopyUtils.copyBean(comment, SgComment.class);
        return  sgCommentService.addComment(sgComment);
    }

    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友链评论列表",notes = "获取友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "页面显示数量")}
    )
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
       return sgCommentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
