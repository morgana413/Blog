package com.controller;

import com.example.domain.dto.AddArticleDto;
import com.example.domain.dto.ArticleDto;
import com.example.domain.entity.Article;
import com.example.domain.entity.ResponseResult;
import com.example.domain.vo.ArticleVo;
import com.example.domain.vo.PageVo;
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

    @GetMapping("/list")
    public ResponseResult listArticles(Integer pageNum, Integer pageSize,Article article) {
        PageVo pageVo = articleService.selectArticlePage(article,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }

    @GetMapping("{id}")
    public ResponseResult findArticleById(@PathVariable("id") Long id){
            ArticleVo articlevo = articleService.getInfo(id);
            return ResponseResult.okResult(articlevo);
    }

    @PutMapping
    public ResponseResult editArticle(@RequestBody ArticleDto articleDto) {
        articleService.edit(articleDto);
        return ResponseResult.okResult();
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteArticleById(@PathVariable("id") Long id){
    articleService.removeById(id);
    return ResponseResult.okResult();
    }
}
