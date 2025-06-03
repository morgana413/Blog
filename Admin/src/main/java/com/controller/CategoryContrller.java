package com.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.domain.entity.Category;
import com.example.domain.entity.ResponseResult;
import com.example.domain.vo.CategoryVo;
import com.example.domain.vo.PageVo;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryContrller {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, Category category){
        PageVo pageVo = categoryService.selectCategoryPage(category,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category){
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category){
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }
}
