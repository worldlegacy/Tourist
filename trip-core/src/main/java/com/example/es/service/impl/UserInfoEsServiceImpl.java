package com.example.es.service.impl;

import com.example.es.domain.UserInfoEs;
import com.example.es.repository.TravelEsRepository;
import com.example.es.repository.UserInfoEsRepository;
import com.example.es.service.IDestinationEsService;
import com.example.es.service.IUserInfoEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/14
 */
@Service
public class UserInfoEsServiceImpl implements IUserInfoEsService {
    @Autowired
    private UserInfoEsRepository repository;
    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public void save(UserInfoEs es) {
        repository.save(es);
    }
}
