package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.es.qo.SearchQuery;
import com.example.mapper.*;
import com.example.service.IDestinationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
@Service
public class DestinationServiceImpl extends ServiceImpl<DestinationMapper, Destination> implements IDestinationService {

    @Autowired
    private DestinationMapper destinationMapper;
    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private StrategyMapper strategyMapper;
    @Autowired
    private TravelMapper travelMapper;
    @Autowired
    private UserinfoMapper userinfoMapper;

    @Override
    public List<Destination> queryByRegionId(Long regionId) {
        List<Destination> destinations = null;
        if (regionId == -1) {
            //中国
            QueryWrapper<Destination> qw = new QueryWrapper<>();
            qw.eq("parent_id", 1);
            destinations = destinationMapper.selectList(qw);
        } else {
            //根据regionId查询region
            Region region = regionMapper.selectById(regionId);
            String refIds = region.getRefIds();
            String[] arr = refIds.split(",");
            //目的地的ids
            List<Long> ids = new ArrayList<>();
            for (String s : arr) {
                long l = Long.parseLong(s);
                ids.add(l);
            }
            destinations = destinationMapper.selectBatchIds(ids);
        }

        //查询children级别地区并设置进去
        for (Destination destination : destinations) {
            QueryWrapper<Destination> qw = new QueryWrapper<>();
            qw.eq("parent_id", destination.getId());
            qw.last("limit 10");
            List<Destination> children = destinationMapper.selectList(qw);
            destination.setChildren(children);
        }

        return destinations;
    }

    //吐司条设置：三级目录结构
    @Override
    public List<Destination> queryToasts(Long destId) {
        List<Destination> list = new ArrayList<>();
        //广州 广东 中国
        createToasts(destId, list);
        //反序排列
        Collections.reverse(list);
        return list;
    }

    //全员检索：目的地查询
    @Override
    public Map<String, Object> selectByQo(SearchQuery qo) {
        //result.strategys
        QueryWrapper<Strategy> qwS = new QueryWrapper<>();
        qwS.eq("dest_name", qo.getKeyword());
        List<Strategy> strategies = strategyMapper.selectList(qwS);
        //result.travels
        QueryWrapper<Travel> qwT = new QueryWrapper<>();
        qwT.eq("dest_name", qo.getKeyword());
        List<Travel> travels = travelMapper.selectList(qwT);
        for (Travel travel : travels) {
            Userinfo author = userinfoMapper.selectById(travel.getAuthorId());
            travel.setAuthor(author);
        }
        //result.users
        QueryWrapper<Userinfo> qwU = new QueryWrapper<>();
        qwU.eq("city", qo.getKeyword());
        List<Userinfo> userinfos = userinfoMapper.selectList(qwU);
        //dest
        QueryWrapper<Destination> qwD = new QueryWrapper<>();
        qwD.eq("name",qo.getKeyword());
        Destination destination = destinationMapper.selectOne(qwD);

        Integer total = null;
        //total
        if(destination != null){
            total = travels.size() + strategies.size() + userinfos.size() + 1;
        }else {
            total = travels.size() + strategies.size() + userinfos.size();
        }
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> child = new HashMap<>();
        child.put("strategys", strategies);
        child.put("travels", travels);
        child.put("users", userinfos);
        child.put("total",total);
        map.put("result", child);
        map.put("dest",destination);
        map.put("qo",qo);
        return map;
    }

    //递归查询
    private void createToasts(Long destId, List<Destination> list) {
        //判断destId是否为空
        if (destId == null) {
            return;
        }
        Destination destination = destinationMapper.selectById(destId);
        list.add(destination);
        //递归调用
        createToasts(destination.getParentId(), list);
    }
}
