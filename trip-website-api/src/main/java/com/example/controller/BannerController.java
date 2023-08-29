package com.example.controller;


import com.example.entity.Banner;
import com.example.service.IBannerService;
import com.example.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
@RestController
@RequestMapping("/banners")
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    @GetMapping("/strategy")
    public JsonResult strategy(){
        List<Banner> banners = bannerService.queryByType(1);
        return JsonResult.success(banners);
    }

    @GetMapping("/travel")
    public JsonResult travel(){
        List<Banner> banners = bannerService.queryByType(2);
        return JsonResult.success(banners);
    }
}

