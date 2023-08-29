package com.example.service;

import com.example.entity.Banner;
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
public interface IBannerService extends IService<Banner> {

    /**
     * banner图
     * @param i
     * @return
     */
    List<Banner> queryByType(int i);
}
