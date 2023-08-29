package com.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ann.CurrentUser;
import com.example.ann.RequireLogin;
import com.example.entity.*;
import com.example.mongo.domain.StrategyComment;
import com.example.mongo.qo.StrategyCommentQuery;
import com.example.mongo.service.IStrategyCommentService;
import com.example.qo.StrategyQuery;
import com.example.redis.service.IStrategyStatisVORedisService;
import com.example.redis.service.IUserInfoRedisService;
import com.example.redis.vo.StrategyStatisVO;
import com.example.service.*;
import com.example.util.JsonResult;
import com.example.vo.ThemeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/strategies")
public class StrategyController {

    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyThemeService strategyThemeService;
    @Autowired
    private IStrategyContentService strategyContentService;
    @Autowired
    private IStrategyRankService strategyRankService;
    @Autowired
    private IStrategyConditionService strategyConditionService;
    @Autowired
    private IUserInfoRedisService userInfoRedisService;
    @Autowired
    private IStrategyCommentService strategyCommentService;
    @Autowired
    private IStrategyStatisVORedisService strategyStatisVORedisService;

    @GetMapping("/query")
    public JsonResult query(StrategyQuery qo) {
        Page<Strategy> page = strategyService.queryPage(qo);
        return JsonResult.success(page);
    }

    //查询攻略主题(全部)
    @GetMapping("/themes")
    public JsonResult themes() {
        List<StrategyTheme> list = strategyThemeService.list();
        return JsonResult.success(list);
    }

    //查询攻略详情（根据id）
    @GetMapping("/detail")
    public JsonResult detail(Long id) {
        Strategy strategy = strategyService.getById(id);
        StrategyContent content = strategyContentService.getById(strategy.getId());
        strategy.setContent(content);
        strategyStatisVORedisService.viewnumIncrease(id);
        return JsonResult.success(strategy);
    }

    //查询攻略排行
    @GetMapping("/rank")
    public JsonResult rank(int type) {
        List<StrategyRank> ranks = strategyRankService.queryRank(type);
        return JsonResult.success(ranks);
    }

    //查询攻略导航
    @GetMapping("/condition")
    public JsonResult condition(int type) {
        List<StrategyCondition> conditions = strategyConditionService.queryCondition(type);
        return JsonResult.success(conditions);
    }

    //评论存储
    @PostMapping("/commentAdd")
    @RequireLogin
    public JsonResult commentAdd(StrategyComment strategyComment, @CurrentUser Userinfo user) {
        strategyCommentService.save(strategyComment, user);
        strategyStatisVORedisService.replyIncrease(strategyComment.getStrategyId());
        return JsonResult.success();
    }

    //评论展示
    @GetMapping("/comments")
    public JsonResult comments(StrategyCommentQuery qo) {
        //重名不同类机制
//        org.springframework.data.domain.Page page = strategyCommentService.queryPage();
        return JsonResult.success(strategyCommentService.queryPage(qo));
    }

    //访问量展示
    @GetMapping("/statisVo")
    public JsonResult statisVo(Long sid) {
        StrategyStatisVO strategyVO = strategyStatisVORedisService.getStrategyVO(sid);
        return JsonResult.success(strategyVO);
    }

    /**
     * 返回值jsonResult中存储一个boolean类型的值，true为收藏成功，false为取消收藏
     */
    @PostMapping("/favor")
    @RequireLogin
    public JsonResult favor(Long sid, @CurrentUser Userinfo userinfo) {
        boolean b = strategyStatisVORedisService.favor(sid, userinfo.getId());
        return JsonResult.success(b);
    }

    @PostMapping("/strategyThumbup")
    @RequireLogin
    public JsonResult strategyThumbup(Long sid,@CurrentUser Userinfo user){
        boolean b = strategyStatisVORedisService.strategyThumbsup(sid,user.getId());
        return JsonResult.success(b);
    }

    //主题推荐
    @GetMapping("/themeCds")
    public JsonResult themeCds(){
        List<ThemeVo> themeVos = strategyThemeService.queryThemeVo();
        return JsonResult.success(themeVos);
    }
}