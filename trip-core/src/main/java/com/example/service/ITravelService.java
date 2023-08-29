package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Travel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.qo.TravelQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
public interface ITravelService extends IService<Travel> {

    /**
     * 游记分页
     * @param qo
     * @return
     */
    Page<Travel> queryPage(TravelQuery qo);

    /**
     * 保存发表的信息
     * @param travel
     * @return
     */
    long insert(Travel travel);

    /**
     * 游记排名
     * @param destId
     * @return
     */
    List<Travel> queryViewnumTop3(Long destId);
}
