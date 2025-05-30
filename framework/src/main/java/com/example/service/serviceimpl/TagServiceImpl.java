package com.example.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.domain.dto.TagDto;
import com.example.domain.dto.TagListDto;
import com.example.domain.entity.ResponseResult;
import com.example.domain.entity.Tag;
import com.example.domain.vo.PageVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.mapper.TagMapper;
import com.example.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2025-05-29 17:40:23
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName, tagListDto.getName());
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

}
