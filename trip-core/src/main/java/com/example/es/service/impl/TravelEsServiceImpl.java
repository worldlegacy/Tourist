package com.example.es.service.impl;

import com.example.es.domain.TravelEs;
import com.example.es.repository.TravelEsRepository;
import com.example.es.service.IDestinationEsService;
import com.example.es.service.ITravelEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/14
 */
@Service
public class TravelEsServiceImpl implements ITravelEsService {
    @Autowired
    private TravelEsRepository repository;
    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public void save(TravelEs es) {
        repository.save(es);
    }
}
