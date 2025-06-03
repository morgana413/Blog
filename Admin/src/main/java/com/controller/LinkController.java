package com.controller;

import com.example.domain.entity.Link;
import com.example.domain.entity.ResponseResult;
import com.example.domain.vo.PageVo;
import com.example.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, Link link) {
        PageVo pageVo = linkService.selectLinkPage(pageNum,pageSize,link);
        return ResponseResult.okResult(pageVo);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody Link link) {
        linkService.save(link);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable Long id) {
        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }

    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link) {
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id) {
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
