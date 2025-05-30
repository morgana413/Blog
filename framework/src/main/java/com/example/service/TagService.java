package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.dto.TagDto;
import com.example.domain.dto.TagListDto;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.Tag;
import com.example.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2025-05-29 17:40:22
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    List<TagVo> listAllTag();
}
