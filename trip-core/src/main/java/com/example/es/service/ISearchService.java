package com.example.es.service;

import com.example.es.qo.SearchQuery;
import org.springframework.data.domain.Page;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/15
 */
public interface ISearchService {
    /**
     * @param indexName 查询的表
     * @param typeName 查询的表
     * @param clazz 查询返回的数据类型的class
     * @param qo 查询条件：分页条件，查询关键字
     * @param fields 查询哪些列
     * @param <T> 查询数据对应的Mysql中存储的类型，不是es中的类型
     */
    <T> Page<T> searchWithHighLight(String indexName, String typeName, Class<T> clazz, SearchQuery qo,String...fields);
}
