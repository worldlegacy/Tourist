package com.example.service;

import com.example.entity.StrategyCatalog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.CatalogVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
public interface IStrategyCatalogService extends IService<StrategyCatalog> {

    /**
     * 查询分类的攻略
     * @param destId
     * @return
     */
    List<StrategyCatalog> queryByDestinationId(Long destId);

    /**
     * 二级分类展示：省，市
     * @return
     */
    List<CatalogVo> queryGroupCatalog();
}
