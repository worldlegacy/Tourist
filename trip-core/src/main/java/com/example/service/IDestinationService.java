package com.example.service;

import com.example.entity.Destination;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.es.qo.SearchQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
public interface IDestinationService extends IService<Destination> {

    /**
     * 二级地区查询
     * @return
     * @param regionId
     */
    List<Destination> queryByRegionId(Long regionId);

    /**
     * toasts条设置
     * @return
     * @param destId
     */
    List<Destination> queryToasts(Long destId);

    /**
     *
     * @param qo
     * @return
     */
    Map<String, Object> selectByQo(SearchQuery qo);
}
