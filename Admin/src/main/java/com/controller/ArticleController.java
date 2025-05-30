package com.controller;

import com.example.domain.dto.AddArticleDto;
import com.example.domain.entity.Article;
import com.example.domain.entity.ResponseResult;
import com.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto) {
    return articleService.addArticle(addArticleDto);
    }
}
