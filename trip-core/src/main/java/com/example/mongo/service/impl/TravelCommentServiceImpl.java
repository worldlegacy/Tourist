package com.example.mongo.service.impl;

import com.example.mongo.domain.TravelComment;
import com.example.mongo.repository.TravelCommentRepository;
import com.example.mongo.service.ITravelCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/10
 */
@Service
public class TravelCommentServiceImpl implements ITravelCommentService {

    @Autowired
    private TravelCommentRepository travelCommentRepository;

    //评论存储
    @Override
    public void save(TravelComment comment) {
        comment.setCreateTime(new Date());
        String refId = comment.getRefComment().getId();
        if (StringUtils.hasLength(refId)) {
            //回复他人评论
            Optional<TravelComment> optional = travelCommentRepository.findById(refId);
            if (optional.isPresent()) {
                TravelComment refComment = optional.get();
                comment.setRefComment(refComment);
            }
        } else {
            //普通评论
        }
        travelCommentRepository.save(comment);
    }

    //评论展示
    @Override
    public List<TravelComment> queryByTravelId(Long travelId) {
        List<TravelComment> travelComments = travelCommentRepository.findByTravelIdOrderByCreateTimeDesc(travelId);
        return travelComments;
    }
}
