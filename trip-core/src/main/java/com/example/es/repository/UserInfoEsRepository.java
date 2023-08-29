package com.example.es.repository;

import com.example.es.domain.DestinationEs;
import com.example.es.domain.UserInfoEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/14
 */
public interface UserInfoEsRepository extends ElasticsearchRepository<UserInfoEs,Long> {

}
