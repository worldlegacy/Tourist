package com.example.es.service.impl;

import com.example.es.domain.DestinationEs;
import com.example.es.repository.DestinationEsRepository;
import com.example.es.service.IDestinationEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/14
 */
@Service
public class DestinationEsServiceImpl implements IDestinationEsService {
    @Autowired
    private DestinationEsRepository repository;
    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public void save(DestinationEs es) {
        repository.save(es);
    }
}
