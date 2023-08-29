package com.example.controller;

import com.example.entity.Destination;
import com.example.entity.Strategy;
import com.example.entity.Travel;
import com.example.entity.Userinfo;
import com.example.es.domain.DestinationEs;
import com.example.es.domain.StrategyEs;
import com.example.es.domain.TravelEs;
import com.example.es.domain.UserInfoEs;
import com.example.es.service.IDestinationEsService;
import com.example.es.service.IStrategyEsService;
import com.example.es.service.ITravelEsService;
import com.example.es.service.IUserInfoEsService;
import com.example.service.IDestinationService;
import com.example.service.IStrategyService;
import com.example.service.ITravelService;
import com.example.service.IUserinfoService;
import com.example.util.JsonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Artist
 * @Description elasticsearch
 * @Date 2023/8/14
 */
@RestController
public class DataController {
    @Autowired
    private IStrategyEsService strategyEsService;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private ITravelEsService travelEsService;
    @Autowired
    private ITravelService travelService;
    @Autowired
    private IDestinationEsService destinationEsService;
    @Autowired
    private IDestinationService destinationService;
    @Autowired
    private IUserInfoEsService userInfoEsService;
    @Autowired
    private IUserinfoService userinfoService;

    //数据初始化
    @GetMapping("/dataInit")
    public JsonResult dataInit() {

        List<Strategy> sts = strategyService.list();
        for (Strategy st : sts) {
            StrategyEs es = new StrategyEs();
            BeanUtils.copyProperties(st, es);
            //攻略保存
            strategyEsService.save(es);
        }

        List<Travel> ts = travelService.list();
        for (Travel t : ts) {
            TravelEs es = new TravelEs();
            BeanUtils.copyProperties(t, es);
            //游记保存
            travelEsService.save(es);
        }

        List<Destination> ds = destinationService.list();
        for (Destination d : ds) {
            DestinationEs es = new DestinationEs();
            BeanUtils.copyProperties(d, es);
            //目的地保存
            destinationEsService.save(es);
        }

        List<Userinfo> us = userinfoService.list();
        for (Userinfo u : us) {
            UserInfoEs es = new UserInfoEs();
            BeanUtils.copyProperties(u, es);
            //用户保存
            userInfoEsService.save(es);
        }
        return JsonResult.success();
    }
}
