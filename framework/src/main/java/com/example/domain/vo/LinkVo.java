package com.example.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class LinkVo {
    @TableId
    private Long id;

    private String logo;

    private String name;

    private String description;
    //网站地址
    private String address;
}
