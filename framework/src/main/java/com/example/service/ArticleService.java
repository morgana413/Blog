package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.entity.Article;
import com.example.domain.entity.ResponseResult;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleLIst();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);
}
