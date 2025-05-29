package com.example.controller;

import com.example.domain.entity.ResponseResult;
import com.example.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private UploadService uploadServicel;

    @PostMapping("/upload")
    private ResponseResult uploadImage(MultipartFile img) {
    return uploadServicel.uploadImg(img);
    }
}
