package com.example.mongo.repository;

import com.example.mongo.domain.TravelComment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/10
 */
public interface TravelCommentRepository extends MongoRepository<TravelComment,String> {

    List<TravelComment> findByTravelIdOrderByCreateTimeDesc(Long travelId);
}
