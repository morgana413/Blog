package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.dto.AddArticleDto;
import com.example.domain.dto.ArticleDto;
import com.example.domain.entity.Article;
import com.example.domain.entity.ResponseResult;
import com.example.domain.vo.ArticleVo;
import com.example.domain.vo.PageVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleLIst();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto addArticleDto);

    PageVo selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    ArticleVo getInfo(Long id);

    void edit(ArticleDto articleDto);
}
