package com.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Strategy;
import com.example.entity.StrategyCatalog;
import com.example.entity.StrategyTheme;
import com.example.qo.StrategyQuery;
import com.example.service.IStrategyCatalogService;
import com.example.service.IStrategyService;
import com.example.service.IStrategyThemeService;
import com.example.util.JsonResult;
import com.example.vo.CatalogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
@Controller
@RequestMapping("/strategy")
public class StrategyController {

    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private IStrategyCatalogService strategyCatalogService;
    @Autowired
    private IStrategyThemeService strategyThemeService;

    //freemarker查询所有：分页+模糊
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") StrategyQuery qo){
        Page<Strategy> page = strategyService.queryPage(qo);
        model.addAttribute("page",page);
        return "strategy/list";
    }

    //切换到增加页面：展示数据
    @RequestMapping("/input")
    public String input(Model model){
        //二级展示：省
        List<CatalogVo> catalogs = strategyCatalogService.queryGroupCatalog();
        model.addAttribute("catalogs",catalogs);
        //主题分类展示
        List<StrategyTheme> list = strategyThemeService.list();
        model.addAttribute("themes",list);
        return "strategy/input";
    }

    //保存按钮
    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Strategy strategy){
        strategyService.insert(strategy);
        return JsonResult.success();
    }


}

