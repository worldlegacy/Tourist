package com.example.controller;

import com.example.ann.CurrentUser;
import com.example.ann.RequireLogin;
import com.example.entity.Travel;
import com.example.entity.Userinfo;
import com.example.service.ITravelService;
import com.example.util.JsonResult;
import com.example.util.UploadUtil;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/10
 */
@RestController
public class UploadController {

    @Autowired
    private ITravelService travelService;

    //游记图片上传
    @PostMapping("/coverImageUpload")
    public String coverImageUpload(MultipartFile pic) throws IOException {
        String path = UploadUtil.uploadAli(pic);
        return path;
    }

}
