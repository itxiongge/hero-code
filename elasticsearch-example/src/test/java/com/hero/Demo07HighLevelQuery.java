package com.hero;

import com.hero.utils.HighLevelClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 高级查询：
 * 布尔查询
 * 范围查询
 * 模糊查询
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo07HighLevelQuery {

    /**
     * 注入es的高级客户端
     */
    @Autowired
    private RestHighLevelClient client;

    /**
     * 布尔查询，多条件的组合
     */
    @Test
    public void boolQuery() throws IOException {
        SearchRequest request = new SearchRequest().indices("hero4").types("_doc");
        /**
         * 构建请求体
         */
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //布尔查询构建器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                //title必须含有小米
                .must(QueryBuilders.matchQuery("title","小米"))
                //title必须不含有电视
                .mustNot(QueryBuilders.matchQuery("title","电视"))
                //title应该含有手机
                .should(QueryBuilders.matchQuery("title","手机"));
        //设置查询方式
        sourceBuilder.query(boolQueryBuilder);
        //设置请求体
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //打印响应结果
        HighLevelClient.printResult(response);
    }
    @Test
    public void rangeQuery() throws IOException {
        SearchRequest request = new SearchRequest().indices("hero4").types("_doc");
        /**
         * 构建请求体
         */
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //设置范围查询，小于5千，大于2千
        sourceBuilder.query(QueryBuilders.rangeQuery("price").lt(5000).gt(2000));
        //设置请求体
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //打印响应结果
        HighLevelClient.printResult(response);
    }
    @Test
    public void fuzzyQuery() throws IOException {
        SearchRequest request = new SearchRequest().indices("hero4").types("_doc");
        /**
         * 构建请求体
         */
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //设置模糊查询，设置查询的偏差值：Fuzziness.ONE
        sourceBuilder.query(QueryBuilders.fuzzyQuery("title","appla").fuzziness(Fuzziness.ONE));
        //设置请求体
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //打印响应结果
        HighLevelClient.printResult(response);
    }

}
