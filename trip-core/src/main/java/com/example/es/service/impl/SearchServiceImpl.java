package com.example.es.service.impl;

import com.example.entity.Destination;
import com.example.entity.Strategy;
import com.example.entity.Travel;
import com.example.entity.Userinfo;
import com.example.es.qo.SearchQuery;
import com.example.es.service.ISearchService;
import com.example.service.IDestinationService;
import com.example.service.IStrategyService;
import com.example.service.ITravelService;
import com.example.service.IUserinfoService;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/15
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private IStrategyService strategyService;
    @Autowired
    private ITravelService travelService;
    @Autowired
    private IUserinfoService userinfoService;
    @Autowired
    private IDestinationService destinationService;

    @Override
    public <T> Page<T> searchWithHighLight(String indexName, String typeName, Class<T> clazz, SearchQuery qo, String... fields) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //设置查询的库和表
        builder.withIndices(indexName).withTypes(typeName);
        builder.withQuery(QueryBuilders.multiMatchQuery(qo.getKeyword(), fields));
        PageRequest pageRequest = PageRequest.of(qo.getCurrentPage() - 1, qo.getPageSize(), Sort.Direction.DESC, "_id");
        builder.withPageable(pageRequest);

        //指定高亮查询
        HighlightBuilder.Field[] fs = new HighlightBuilder.Field[fields.length];
        String preTag = "<span style='color:red'>";
        String postTag = "</span>";
        for (int i = 0; i < fields.length; i++) {
            fs[i] = new HighlightBuilder.Field(fields[i]).preTags(preTag).postTags(postTag);
        }
        builder.withHighlightFields(fs);

        //查询
        AggregatedPage<T> page = template.queryForPage(builder.build(), clazz, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                SearchHit[] searchHits = response.getHits().getHits();
                List<T> list = new ArrayList();
                for (SearchHit searchHit : searchHits) {
                    T t = (T) mapSearchHit(searchHit, clazz);
                    list.add(t);
                }
                AggregatedPageImpl<T> result = new AggregatedPageImpl<>(list, pageRequest, response.getHits().getTotalHits());
                return result;
            }

            //返回mysql中数据
            @SneakyThrows
            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> type) {
                T t = null;
                //得到mysql中的完整对象
                Map<String, Object> map = searchHit.getSourceAsMap();
                Long oid = Long.parseLong(map.get("id").toString());
                if (type == Strategy.class) {
                    t = (T) strategyService.getById(oid);
                }
                else if (type == Travel.class) {
                    t = (T) travelService.getById(oid);
                }
                else if (type == Userinfo.class) {
                    t = (T) userinfoService.getById(oid);
                }
                else if (type == Destination.class) {
                    t = (T) destinationService.getById(oid);
                }

                //高亮值在数组中，进行遍历
                for (HighlightField field : searchHit.getHighlightFields().values()) {
                    //将对象对应key的值替换
                    BeanUtils.setProperty(t,field.getName(),field.getFragments()[0].toString());
                }
                //返回高亮数据
                return t;
            }
        });
        return page;
    }
}
