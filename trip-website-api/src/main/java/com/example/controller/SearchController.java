package com.example.controller;

import com.example.entity.Destination;
import com.example.entity.Strategy;
import com.example.entity.Travel;
import com.example.entity.Userinfo;
import com.example.es.domain.DestinationEs;
import com.example.es.domain.StrategyEs;
import com.example.es.domain.TravelEs;
import com.example.es.domain.UserInfoEs;
import com.example.es.qo.SearchQuery;
import com.example.es.service.ISearchService;
import com.example.service.IDestinationService;
import com.example.service.IUserinfoService;
import com.example.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Artist
 * @Description 全文检索
 * @Date 2023/8/15
 */
@RestController
public class SearchController {

    @Autowired
    private ISearchService searchService;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IDestinationService destinationService;

    @GetMapping("/q")
    public JsonResult search(SearchQuery qo) throws UnsupportedEncodingException {
        System.err.println(qo.getKeyword());
        //转码：抛出不支持的格式异常
        String kw = URLDecoder.decode(qo.getKeyword(), "utf-8");
        //转码后存回qo
        qo.setKeyword(kw);
        System.err.println(qo.getKeyword());
        switch (qo.getType()){
            case 0:
                return searchDest(qo);
            case 1:
                return searchStrategy(qo);
            case 2:
                return searchTravel(qo);
            case 3:
                return searchUser(qo);
            default:
                return searchAll(qo);
        }
    }

    private JsonResult searchAll(SearchQuery qo) {
        List<Userinfo> userinfos = searchService.searchWithHighLight(UserInfoEs.INDEX_NAME, UserInfoEs.TEXT_NAME, Userinfo.class, qo,
                "nickname", "city", "info").getContent();
        List<Strategy> strategies = searchService.searchWithHighLight(StrategyEs.INDEX_NAME, StrategyEs.TEXT_NAME, Strategy.class, qo,
                "title", "subTitle", "summary").getContent();
        List<Travel> travels = searchService.searchWithHighLight(TravelEs.INDEX_NAME, TravelEs.TEXT_NAME, Travel.class, qo,
                "title", "summary").getContent();
        for (Travel travel : travels) {
            Userinfo author = userinfoService.getById(travel.getAuthorId());
            travel.setAuthor(author);
        }
        List<Destination> destinations = searchService.searchWithHighLight(DestinationEs.INDEX_NAME, DestinationEs.TEXT_NAME, Destination.class, qo,
                "name", "info").getContent();
        int total = userinfos.size() + strategies.size() + travels.size() + destinations.size();
        Map<String,Object> child = new HashMap<>();
        child.put("total",total);
        child.put("dests",destinations);
        child.put("travels",travels);
        child.put("strategys",strategies);
        child.put("users",userinfos);

        Map<String,Object> map = new HashMap<>();
        map.put("qo",qo);
        map.put("result",child);
        return JsonResult.success(map);
    }

    private JsonResult searchUser(SearchQuery qo) {
        Page<Userinfo> userinfos = searchService.searchWithHighLight(
                UserInfoEs.INDEX_NAME,
                UserInfoEs.TEXT_NAME,
                Userinfo.class,
                qo,
                "nickname", "city", "info"
        );
        Map<String,Object> map = new HashMap<>();
        map.put("page",userinfos);
        map.put("qo",qo);
        return JsonResult.success(map);
    }

    private JsonResult searchTravel(SearchQuery qo) {
        Page<Travel> travels = searchService.searchWithHighLight(
                TravelEs.INDEX_NAME,
                TravelEs.TEXT_NAME,
                Travel.class,
                qo,
                "title", "summary"
        );
        Map<String,Object> map = new HashMap<>();
        map.put("page",travels);
        map.put("qo",qo);
        return JsonResult.success(map);
    }

    private JsonResult searchStrategy(SearchQuery qo) {
        Page<Strategy> strategies = searchService.searchWithHighLight(StrategyEs.INDEX_NAME, StrategyEs.TEXT_NAME, Strategy.class, qo, "title", "subTitle","summary");
        Map<String,Object> map = new HashMap<>();
        map.put("page",strategies);
        map.put("qo",qo);
        return JsonResult.success(map);
    }

    private JsonResult searchDest(SearchQuery qo) {
        Map<String,Object> map = destinationService.selectByQo(qo);
        return JsonResult.success(map);
    }
}
