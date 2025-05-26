package com.example.sino.controller;

import com.example.domain.entity.Article;
import com.example.domain.entity.ResponseResult;
import com.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
@ComponentScan(basePackages = {"com.example.service", "com.example.sino.controller"})
public class ArticleController {
    @Autowired
    private ArticleService articleService;

/*    @GetMapping("/text")
    public List<Article> text(){
        return articleService.list();
    }*/
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
    ResponseResult result = articleService.hotArticleLIst();
    return result;
    }

}
