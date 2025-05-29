package com.example.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.SgComment;
import com.example.domain.vo.CommentVo;
import com.example.domain.vo.PageVo;
import com.example.mapper.SgCommentMapper;
import com.example.service.SgCommentService;
import com.example.service.UserService;
import com.example.utils.BeanCopyUtils;
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
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
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
}
