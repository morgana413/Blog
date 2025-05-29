package com.example.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.entity.Article;
import com.example.domain.entity.Category;
import com.example.domain.entity.ResponseResult;
import com.example.domain.vo.ArticleDetailVo;
import com.example.domain.vo.ArticleListVo;
import com.example.domain.vo.HotArticleVo;
import com.example.domain.vo.PageVo;
import com.example.mapper.ArticleMapper;
import com.example.service.ArticleService;

import com.example.service.CategoryService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("ArticleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult hotArticleLIst() { //查询热门文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查询10条
        Page<Article> articlePage = new Page<>(1, 10);
        page(articlePage, queryWrapper);

        List<Article> articleList = articlePage.getRecords();

       /* List<HotArticleVo> hotArticleVoList = new ArrayList<>();
        for (Article article : articleList) {
            HotArticleVo hotArticleVo = new HotArticleVo();
            BeanUtils.copyProperties(article,hotArticleVo);
            hotArticleVoList.add(hotArticleVo);
        }*/

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articleList, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId 就要 查询时和传入的相同
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId > 0, Article::getCategoryId, categoryId);
        //状态是正式发布的
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //对IsTop进行降序
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        page(articlePage, queryWrapper);
        //查询categoryName
        List<Article> articleList = articlePage.getRecords();
        for (Article article : articleList) {
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());
        }
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articlePage.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,articlePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取文章浏览量
        Integer viewCount = redisCache.getCacheMapValue("article", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Category category = categoryService.getById(article.getCategoryId());
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {

        redisCache.incrementCacheMapValue("article",id.toString(),1);
        return ResponseResult.okResult();
    }
}
