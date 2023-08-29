package com.example.service;

import com.example.entity.Region;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
public interface IRegionService extends IService<Region> {
    /**
     * 查询地区
     * @return
     */
    List<Region> queryHotRegion();
}
