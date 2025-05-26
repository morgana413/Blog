package com.example.service.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.entity.Article;
import com.example.mapper.ArticleMapper;
import com.example.service.ArticleService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
