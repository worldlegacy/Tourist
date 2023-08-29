package com.example.service;

import com.example.entity.Strategy;
import com.example.entity.StrategyTheme;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.ThemeVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
public interface IStrategyThemeService extends IService<StrategyTheme> {

    List<ThemeVo> queryThemeVo();

}
