package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Destination;
import com.example.entity.Travel;
import com.example.entity.TravelContent;
import com.example.entity.Userinfo;
import com.example.mapper.DestinationMapper;
import com.example.mapper.TravelContentMapper;
import com.example.mapper.TravelMapper;
import com.example.mapper.UserinfoMapper;
import com.example.qo.TravelCondition;
import com.example.qo.TravelQuery;
import com.example.service.ITravelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Artist
 * @since 2023-08-01
 */
@Service
public class TravelServiceImpl extends ServiceImpl<TravelMapper, Travel> implements ITravelService {

    @Autowired
    private TravelMapper travelMapper;
    @Autowired
    private UserinfoMapper userinfoMapper;
    @Autowired
    private DestinationMapper destinationMapper;
    @Autowired
    private TravelContentMapper travelContentMapper;

    //游记分页
    @Override
    public Page<Travel> queryPage(TravelQuery qo) {
        QueryWrapper<Travel> qw = new QueryWrapper<>();
        qw.eq(qo.getDestId()!=null,"dest_id",qo.getDestId());
        //排序条件
        qw.orderByDesc(qo.getOrderBy());
        //根据出行天数查询
        TravelCondition dayType = TravelCondition.DAY_MAP.get(qo.getDayType());
        if (dayType != null) {
            qw.ge("day", dayType.getMin());
            qw.le("day", dayType.getMax());
        }

        //根据平均消费查询
        TravelCondition consumeType = TravelCondition.AVG_MAP.get(qo.getConsumeType());
        if (consumeType != null) {
            qw.ge("avg_consume", consumeType.getMin());
            qw.le("avg_consume", consumeType.getMax());
        }

        //根据出发时间查询
        TravelCondition travelTimeType = TravelCondition.TIME_MAP.get(qo.getTravelTimeType());
        if (travelTimeType != null) {
            qw.ge("MONTH(travel_time)", travelTimeType.getMin());
            qw.le("MONTH(travel_time)", travelTimeType.getMax());
        }

        Page<Travel> page = new Page<>();
        page.setCurrent(qo.getCurrentPage());
        page.setSize(qo.getPageSize());

        Page<Travel> pageResult = travelMapper.selectPage(page, qw);
        //查询作者对象，前端页面要用作者的头像heaImgUrl
        for (Travel record : pageResult.getRecords()) {
            Userinfo user = userinfoMapper.selectById(record.getAuthorId());
            record.setAuthor(user);
        }
        return pageResult;
    }

    //保存travel表数据
    @Override
    public long insert(Travel travel) {
        Destination destination = destinationMapper.selectById(travel.getDestId());
        travel.setDestName(destination.getName());
        travel.setViewnum(0);
        travel.setReplynum(0);
        travel.setFavornum(0);
        travel.setSharenum(0);
        travel.setThumbsupnum(0);
        Date now = new Date();
        travel.setCreateTime(now);
        travel.setLastUpdateTime(now);
        travelMapper.insert(travel);

        TravelContent content = new TravelContent();
        content.setId(travel.getId());
        content.setContent(travel.getContent().getContent());
        travelContentMapper.insert(content);
        return travel.getId();
    }

    //游记排名Top3
    @Override
    public List<Travel> queryViewnumTop3(Long destId) {
        QueryWrapper<Travel> qw = new QueryWrapper<>();
        qw.eq("dest_id",destId);
        qw.orderByDesc("viewnum");
        qw.last("limit 3");
        List<Travel> list = travelMapper.selectList(qw);
        return list;
    }
}
