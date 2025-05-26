package com.example.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.entity.Article;
import com.example.domain.entity.ResponseResult;
import com.example.mapper.ArticleMapper;
import com.example.service.ArticleService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public ResponseResult hotArticleLIst() { //查询热门文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus,0);
        //按照浏览量进行排序
        queryWrapper.orderByAsc(Article::getViewCount);
        //最多查询10条
        Page<Article> articlePage = new Page<>(1, 10);
        page(articlePage, queryWrapper);

        List<Article> articleList = articlePage.getRecords();
        return ResponseResult.okResult(articleList);
    }
}
