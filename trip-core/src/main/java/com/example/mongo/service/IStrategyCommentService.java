package com.example.mongo.service;

import com.example.entity.Userinfo;
import com.example.mongo.domain.StrategyComment;
import com.example.mongo.qo.StrategyCommentQuery;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/8
 */
public interface IStrategyCommentService {

    /**
     * 保存评论的相关信息
     * @param strategyComment
     * @param user
     */
    void save(StrategyComment strategyComment, Userinfo user);

    /**
     * 评论显示
     * @param qo
     * @return
     */
    Object queryPage(StrategyCommentQuery qo);
}
