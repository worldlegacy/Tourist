package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Region;
import com.example.mapper.RegionMapper;
import com.example.service.IRegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements IRegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<Region> queryHotRegion() {
        QueryWrapper<Region> qw = new QueryWrapper<>();
        qw.eq("ishot",1);
        qw.orderByAsc("seq");
        return regionMapper.selectList(qw);
    }
}
