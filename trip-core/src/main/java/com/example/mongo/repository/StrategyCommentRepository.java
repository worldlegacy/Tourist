package com.example.mongo.repository;

import com.example.mongo.domain.StrategyComment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/8
 */
public interface StrategyCommentRepository extends MongoRepository<StrategyComment,String> {

}
