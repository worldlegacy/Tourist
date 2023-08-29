package com.example.mongo.service;

import com.example.mongo.domain.TravelComment;

import java.util.List;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/10
 */
public interface ITravelCommentService {
    /**
     * 发出评论
     * @param comment
     */
    void save(TravelComment comment);

    /**
     * 评论展示
     * @param travelId
     * @return
     */
    List<TravelComment> queryByTravelId(Long travelId);
}
