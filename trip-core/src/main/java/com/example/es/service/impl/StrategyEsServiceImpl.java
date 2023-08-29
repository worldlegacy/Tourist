package com.example.es.service.impl;

import com.example.es.domain.StrategyEs;
import com.example.es.repository.DestinationEsRepository;
import com.example.es.repository.StrategyEsRepository;
import com.example.es.service.IStrategyEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/14
 */
@Service
public class StrategyEsServiceImpl implements IStrategyEsService {

    @Autowired
    private StrategyEsRepository repository;
    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public void save(StrategyEs es) {
        repository.save(es);
    }
}
