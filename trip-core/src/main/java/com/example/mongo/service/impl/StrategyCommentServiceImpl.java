package com.example.mongo.service.impl;

import com.example.entity.Userinfo;
import com.example.mongo.domain.StrategyComment;
import com.example.mongo.qo.StrategyCommentQuery;
import com.example.mongo.repository.StrategyCommentRepository;
import com.example.mongo.service.IStrategyCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/8
 */
@Service
public class StrategyCommentServiceImpl implements IStrategyCommentService {

    @Autowired
    private StrategyCommentRepository repository;
    @Autowired
    private MongoTemplate template;
    //保存评论的相关信息
    @Override
    public void save(StrategyComment strategyComment, Userinfo user) {
        strategyComment.setCreateTime(new Date());
        strategyComment.setUserId(user.getId());
        strategyComment.setNickName(user.getNickname());
        strategyComment.setLevel(user.getLevel());
        strategyComment.setCity(user.getCity());
        strategyComment.setHeadImgUrl(user.getHeadImgUrl());
        repository.save(strategyComment);
    }

    //评论展示
    @Override
    public Object queryPage(StrategyCommentQuery qo) {
        Query query = new Query();
        Criteria criteria = Criteria.where("strategyId").is(qo.getStrategyId());
        query.addCriteria(criteria);
        long totalCount = template.count(query, StrategyComment.class);
        if(totalCount == 0){
            return Page.empty();
        }
        PageRequest pageRequest = PageRequest.of(qo.getCurrentPage() - 1, qo.getPageSize());
        query.with(pageRequest);
        query.with(Sort.by(Sort.Direction.DESC,"createTime"));
        List<StrategyComment> strategyComments = template.find(query, StrategyComment.class);
        PageImpl<StrategyComment> pageResult = new PageImpl<>(strategyComments, pageRequest, totalCount);
        return pageResult;
    }
}















