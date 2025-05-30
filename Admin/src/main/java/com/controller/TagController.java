package com.controller;

import com.example.domain.dto.TagDto;
import com.example.domain.dto.TagListDto;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.Tag;
import com.example.service.TagService;
import com.example.utils.BeanCopyUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageSize, Integer pageNum, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize, tagListDto);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagDto tagDto){
      Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        tagService.removeById(id);
        return ResponseResult.okResult();
    }
}
