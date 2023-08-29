package com.example.controller;


import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.entity.*;
import com.example.service.*;
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
@RequestMapping("/destinations")
public class DestinationController {

    @Autowired
    private IDestinationService destinationService;
    @Autowired
    private IRegionService regionService;
    @Autowired
    private IStrategyCatalogService strategyCatalogService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private ITravelService travelService;

    //游记目的地下拉
    @GetMapping("/list")
    public JsonResult list(){
        List<Destination> dests = destinationService.list();
        return JsonResult.success(dests);
    }

    //查询一级地区信息，显示在目的地页面顶部
    @GetMapping("/hotRegion")
    public JsonResult hotRegion(){
        List<Region> list = regionService.queryHotRegion();
        return JsonResult.success(list);
    }

    //查询二级地区信息，显示在选中的一级地区下面
    @GetMapping("/search")
    public JsonResult search(Long regionId) {
        List<Destination> destinations = destinationService.queryByRegionId(regionId);
        return JsonResult.success(destinations);
    }

    //设置吐司条，返回 中国 广东 广州三级目录结构
    @GetMapping("/toasts")
    public JsonResult toasts(Long destId){
        List<Destination> list = destinationService.queryToasts(destId);
        return JsonResult.success(list);
    }

    @GetMapping("/catalogs")
    public JsonResult catalogs(Long destId){
        //查询分类的攻略
        List<StrategyCatalog> list = strategyCatalogService.queryByDestinationId(destId);
        return JsonResult.success(list);
    }

    @GetMapping("/strategies/viewnumTop3")
    public JsonResult viewnumTop3(Long destId){
        List<Strategy> list = strategyService.queryViewnumTop3(destId);
        return JsonResult.success(list);
    }

    //目的地详情页 游记数据查询
    @GetMapping("/travels/viewnumTop3")
    public JsonResult travel_viewnumTop3(Long destId){
        List<Travel> list = travelService.queryViewnumTop3(destId);
        return JsonResult.success(list);
    }
}

