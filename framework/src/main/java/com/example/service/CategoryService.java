package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.entity.Category;
import com.example.domain.entity.ResponseResult;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2025-05-26 14:47:32
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
