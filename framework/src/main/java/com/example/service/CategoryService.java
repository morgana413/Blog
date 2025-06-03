package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.entity.Category;
import com.example.domain.entity.ResponseResult;
import com.example.domain.vo.CategoryVo;
import com.example.domain.vo.PageVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2025-05-26 14:47:32
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize);
}
