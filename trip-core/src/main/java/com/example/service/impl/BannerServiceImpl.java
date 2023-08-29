package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Banner;
import com.example.mapper.BannerMapper;
import com.example.service.IBannerService;
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
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Autowired
    private BannerMapper bannerMapper;

    //banner图设置
    @Override
    public List<Banner> queryByType(int i) {
        QueryWrapper<Banner>qw = new QueryWrapper<>();
        qw.eq("type",i);
        qw.orderByAsc("seq");
        qw.eq("state",0);
        qw.last("limit 5");
        List<Banner> banners = bannerMapper.selectList(qw);
        return banners;
    }
}
