package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.entity.Link;
import com.example.domain.entity.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2025-05-28 11:46:23
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
