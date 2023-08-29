package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Destination;
import com.example.entity.Strategy;
import com.example.entity.StrategyTheme;
import com.example.mapper.DestinationMapper;
import com.example.mapper.StrategyMapper;
import com.example.mapper.StrategyThemeMapper;
import com.example.service.IStrategyThemeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vo.ThemeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
@Service
public class StrategyThemeServiceImpl extends ServiceImpl<StrategyThemeMapper, StrategyTheme> implements IStrategyThemeService {

    @Autowired
    private StrategyMapper strategyMapper;

    @Override
    public List<ThemeVo> queryThemeVo() {
        QueryWrapper<Strategy> qw = new QueryWrapper<>();
        qw.groupBy("theme_id","theme_name");
        qw.select("theme_id,theme_name,group_concat(distinct dest_id) ids,group_concat(distinct dest_name) names");
        qw.last("limit 10");
        List<Map<String, Object>> maps = strategyMapper.selectMaps(qw);
        List<ThemeVo> themeVos = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            String themeName = map.get("theme_name").toString();
            String ids = map.get("ids").toString();
            String names = map.get("names").toString();
            String[] is = ids.split(",");
            String[] ns = names.split(",");
            List<Destination> destinations = new ArrayList<>();
            for (int i = 0; i < is.length; i++) {
                long destId = Long.parseLong(is[i]);
                String destName = ns[i];
                Destination destination = new Destination();
                destination.setId(destId);
                destination.setName(destName);
                destinations.add(destination);
            }

            ThemeVo themeVo = new ThemeVo();
            themeVo.setThemeName(themeName);
            themeVo.setDests(destinations);
            themeVos.add(themeVo);
        }
        return themeVos;
    }
}
