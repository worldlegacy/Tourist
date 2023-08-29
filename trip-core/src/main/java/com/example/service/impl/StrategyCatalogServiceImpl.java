package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Strategy;
import com.example.entity.StrategyCatalog;
import com.example.mapper.StrategyCatalogMapper;
import com.example.mapper.StrategyMapper;
import com.example.service.IStrategyCatalogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vo.CatalogVo;
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
public class StrategyCatalogServiceImpl extends ServiceImpl<StrategyCatalogMapper, StrategyCatalog> implements IStrategyCatalogService {

    @Autowired
    private StrategyCatalogMapper strategyCatalogMapper;
    @Autowired
    private StrategyMapper strategyMapper;
    @Override
    public List<StrategyCatalog> queryByDestinationId(Long destId) {
        QueryWrapper<StrategyCatalog> qw = new QueryWrapper<>();
        qw.eq("dest_id", destId);
        List<StrategyCatalog> list = strategyCatalogMapper.selectList(qw);
        for (StrategyCatalog catalog : list) {
            QueryWrapper<Strategy> strategyQw = new QueryWrapper<>();
            strategyQw.eq("catalog_id", catalog.getId());
            List<Strategy> strategies = strategyMapper.selectList(strategyQw);
            catalog.setStrategies(strategies);
        }
        return list;
    }

    //跳转到增加页面
    @Override
    public List<CatalogVo> queryGroupCatalog() {
        QueryWrapper<StrategyCatalog> qw = new QueryWrapper<>();
        qw.groupBy("dest_id","dest_name");
        qw.select("dest_id,dest_name,GROUP_CONCAT(id) ids,GROUP_CONCAT(name) names");
        List<Map<String, Object>> maps = strategyCatalogMapper.selectMaps(qw);
        List<CatalogVo> catalogVos = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            String destName = map.get("dest_name").toString();
            String names = map.get("names").toString();
            String ids = map.get("ids").toString();
            String[] ns = names.split(",");
            String[] is = ids.split(",");
            List<StrategyCatalog> catalogs = new ArrayList<>();
            for (int i = 0; i < is.length; i++) {
                Long id = Long.parseLong(is[i]);
                String name = ns[i];
                StrategyCatalog catalog = new StrategyCatalog();
                catalog.setId(id);
                catalog.setName(name);
                catalogs.add(catalog);
            }
            CatalogVo vo = new CatalogVo();
            vo.setDestName(destName);
            vo.setCatalogList(catalogs);
            catalogVos.add(vo);
        }
        return catalogVos;
    }
}





























