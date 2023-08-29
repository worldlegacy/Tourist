package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.*;
import com.example.mapper.*;
import com.example.qo.StrategyQuery;
import com.example.redis.vo.StrategyStatisVO;
import com.example.service.IDestinationService;
import com.example.service.IStrategyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
@Service
public class StrategyServiceImpl extends ServiceImpl<StrategyMapper, Strategy> implements IStrategyService {

    @Autowired
    private StrategyMapper strategyMapper;
    @Autowired
    private StrategyCatalogMapper strategyCatalogMapper;
    @Autowired
    private StrategyThemeMapper strategyThemeMapper;
    @Autowired
    private IDestinationService destinationService;
    @Autowired
    private StrategyContentMapper strategyContentMapper;

    //查询攻略
    @Override
    public List<Strategy> queryViewnumTop3(Long destId) {
        QueryWrapper<Strategy> qw = new QueryWrapper<>();
        qw.eq("dest_id", destId);
        qw.orderByDesc("viewnum");
        qw.last("limit 3");
        List<Strategy> list = strategyMapper.selectList(qw);
        return list;
    }

    //分页查询总攻略信息
    @Override
    public Page<Strategy> queryPage(StrategyQuery qo) {
        QueryWrapper<Strategy> qw = new QueryWrapper<>();
        qw.eq(qo.getDestId() != null, "dest_id", qo.getDestId());
        qw.eq(qo.getThemeId() != null, "theme_id", qo.getThemeId());

        if(qo.getType() != null){
            if(qo.getType() == 3){
                qw.eq("theme_id",qo.getRefid());
            }
            else if(qo.getType() == 1 || qo.getType() == 2){
                qw.eq("dest_id",qo.getRefid());
            }
        }

        qw.orderByDesc(qo.getOrderBy());

        Page<Strategy> page = new Page<>();
        page.setCurrent(qo.getCurrentPage());
        page.setSize(qo.getPageSize());
        Page<Strategy> pages = strategyMapper.selectPage(page,qw);
        return pages;
    }

    //保存功能
    @Override
    public void insert(Strategy strategy) {
        //分类表查询目的地id 目的地名字 分类名字
        StrategyCatalog catalog = strategyCatalogMapper.selectById(strategy.getCatalogId());
        strategy.setDestId(catalog.getDestId());
        strategy.setDestName(catalog.getDestName());
        strategy.setCatalogName(catalog.getName());
        //主题表查询主题
        StrategyTheme strategyTheme = strategyThemeMapper.selectById(strategy.getThemeId());
        strategy.setThemeName(strategyTheme.getName());
        strategy.setCreateTime(new Date());
        //判断目的地是国内还是国外
        List<Destination> list = destinationService.queryToasts(catalog.getDestId());
        if(list != null && list.size() > 0){
            if(list.get(0).getName().equals("中国")){
                //国内
                strategy.setIsabroad(0);
            }else {
                //国外
                strategy.setIsabroad(1);
            }
        }
        //初始化统计数据默认为0
        strategy.setViewnum(0);
        strategy.setReplynum(0);
        strategy.setFavornum(0);
        strategy.setSharenum(0);
        strategy.setThumbsupnum(0);
        //第一张表保存
        strategyMapper.insert(strategy);
        //第二张表保存
        StrategyContent sc = new StrategyContent();
        sc.setId(strategy.getId());
        sc.setContent(strategy.getContent().getContent());
        strategyContentMapper.insert(sc);
    }

    //redis持久化:落地
    @Override
    public void updateStatisVO(StrategyStatisVO vo) {
        UpdateWrapper<Strategy> uw = new UpdateWrapper<>();
        uw.eq("id",vo.getStrategyId());
        uw.set("viewnum",vo.getViewnum());
        uw.set("replynum",vo.getReplynum());
        uw.set("sharenum",vo.getSharenum());
        uw.set("favornum",vo.getFavornum());
        uw.set("thumbsupnum",vo.getThumbsupnum());
        strategyMapper.update(null,uw);
    }
}
