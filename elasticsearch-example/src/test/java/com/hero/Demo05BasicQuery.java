package com.hero;

import com.hero.utils.HighLevelClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 基本查询
 * 1.查询所有matchAll
 * 2.匹配查询match查询
 * 3.多字段的match查询
 * 4.关键词精确匹配查询(term)
 * 5.多关键词精确的匹配(terms)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo05BasicQuery {

    /**
     * 注入es的高级客户端
     */
    @Autowired
    private RestHighLevelClient client;

    /**
     * 查询所有matchAll
     */
    @Test
    public void matchAllQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        //设置索引库
        request.indices("hero1");
        //查询对象构建器对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置查询方式：matchAll。QueryBuilders.matchAllQuery()
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //设置查询请求体对象
        request.source(searchSourceBuilder);
        //执行请求发送，返回响应对象
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //打印结果
        HighLevelClient.printResult(response);
    }



    /**
     * 匹配查询
     */
    @Test
    public void matchQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        //设置索引库
        request.indices("hero1");
        //查询对象构建器对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //设置查询方式：匹配查询，带分词器的查询
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "小米手机");
        //设置分词后的关键词匹配关系
        matchQueryBuilder.operator(Operator.AND);

        searchSourceBuilder.query(matchQueryBuilder);
        //设置查询请求体对象
        request.source(searchSourceBuilder);
        //执行请求发送，返回响应对象
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //响应对象中抽取查询结果信息
        //打印结果
        HighLevelClient.printResult(response);
    }
    /**
     *  多字段的match查询
     *
     */
    @Test
    public void multiMatchQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        //设置索引库
        request.indices("hero1");
        //查询对象构建器对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //多字段的match查询
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("小米","title","subtitle"));
        //设置查询请求体对象
        request.source(searchSourceBuilder);
        //执行请求发送，返回响应对象
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //响应对象中抽取查询结果信息
        //打印结果
        HighLevelClient.printResult(response);
    }
    /**
     * 关键词精确匹配查询(term)
     * 多关键词精确的匹配(terms)
     */
    @Test
    public void termQuery() throws IOException {
        SearchRequest request = new SearchRequest();
        //设置索引库
        request.indices("hero1");
        //查询对象构建器对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //关键词精确匹配查询(term)
        //searchSourceBuilder.query(QueryBuilders.termQuery("title","小米手机"));
        //多关键词精确的匹配(terms)
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("price", "2699", "3699");
        searchSourceBuilder.query(termsQueryBuilder);
        //设置查询请求体对象
        request.source(searchSourceBuilder);
        //执行请求发送，返回响应对象
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //响应对象中抽取查询结果信息
        //打印结果
        HighLevelClient.printResult(response);
    }
}
