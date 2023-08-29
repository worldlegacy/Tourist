package com.example.controller;

import com.aliyuncs.exceptions.ClientException;
import com.example.exception.LogicException;
import com.example.util.UploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Artist
 * @Description 上传类
 * @Date 2023/8/4
 */
@Controller
public class UploadController {
    @RequestMapping("/uploadImg")
    @ResponseBody
    public String uploadImg(MultipartFile pic) throws IOException, ClientException {
        //将图片上传到阿里云服务器，得到图片地址
        String path = UploadUtil.uploadAli(pic);
        //返回图片地址
        return path;
    }

    //上传:涉及到ckeditor的返回值类型
    @RequestMapping("/uploadImg_ck")
    @ResponseBody
    public Object uploadImg_ck(MultipartFile upload){
        if(upload != null && upload.getSize() > 0){
            Map<String,Object> map = new HashMap<>();
            try {
                String imagePath = UploadUtil.uploadAli(upload);
                map.put("uploaded",1);
                map.put("url",imagePath);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("uploaded",0);
                Map<String,Object> err = new HashMap<>();
                err.put("message",e.getMessage());
                map.put("err",err);
            }
            return map;
        }else {
            throw new LogicException("不能上传空文件");
        }
    }
}
