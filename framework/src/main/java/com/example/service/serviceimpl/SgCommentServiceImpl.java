package com.example.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.SgComment;
import com.example.domain.vo.CommentVo;
import com.example.domain.vo.PageVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.mapper.SgCommentMapper;
import com.example.service.SgCommentService;
import com.example.service.UserService;
import com.example.utils.BeanCopyUtils;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论表(SgComment)表服务实现类
 *
 * @author makejava
 * @since 2025-05-28 17:51:35
 */
@Service("sgCommentService")
public class SgCommentServiceImpl extends ServiceImpl<SgCommentMapper, SgComment> implements SgCommentService {

    @Autowired
    private UserService userService;
    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<SgComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SgComment::getArticleId,articleId);
        wrapper.eq(SgComment::getRootId,-1);

        Page<SgComment> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询所有根评论对应的子评论集合
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(SgComment sgComment) {
        if(!Strings.hasText(sgComment.getContent())){
            throw new SystemException(AppHttpCodeEnum.COMMENT_NOT_NULL);
        }
        save(sgComment);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    private List<CommentVo> toCommentVoList(List<SgComment> sgCommentList) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(sgCommentList, CommentVo.class);
        for (CommentVo commentVo : commentVos) {
            String userName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(userName);
            if (commentVo.getToCommentUserId() != -1){
                String ToCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(ToCommentUserName);
            }
        }
        return commentVos;
    }

    private List<CommentVo> getChildren(Long id) {
    LambdaQueryWrapper<SgComment> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(SgComment::getRootId,id);
    wrapper.orderByAsc(SgComment::getCreateTime);
    List<SgComment> sgCommentList = list(wrapper);
        return toCommentVoList(sgCommentList);
    }
}
