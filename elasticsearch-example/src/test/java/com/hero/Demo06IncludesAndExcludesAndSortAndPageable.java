package com.hero;

import com.hero.utils.HighLevelClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 查询结果过滤、分页、排序
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo06IncludesAndExcludesAndSortAndPageable {

    /**
     * 注入es的高级客户端
     */
    @Autowired
    private RestHighLevelClient client;

    /**
     * 结果过滤、分页、排序，matchAll
     * 1.创建查询对象SearchRequest
     *      设置索引库
     *      设置类型
     *      构建一个请求体
     * 2.客户端发送请求，获取响应对象
     * 3.打印结果信息
     */
    @Test
    public void includesAndExcludes() throws IOException {
        //1.创建查询对象SearchRequest
        SearchRequest request = new SearchRequest().indices("hero4").types("_doc");
        //构建一个请求体 ，Source(请求体，源)
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        /**
         * 结果过滤
         */
        String[] includes = {"title","price"};
        String[] excludes = {};
        sourceBuilder.fetchSource(includes,excludes);
        /**
         * 设置排序
         */
        sourceBuilder.sort("price", SortOrder.DESC);
        /**
         * 分页，
         * currentPage
         * PageSize
         */
        sourceBuilder.size(3);//每页显示多少条
        sourceBuilder.from(1);//当前页起始索引 int form = (currentPage - 1) * pageSize；

        //设置查询方式 ，matchAll
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        //设置请求体
        request.source(sourceBuilder);
        // 2.客户端发送请求，获取响应对象。Request Options(设置请求头，设置响应缓存，设置异常回调处理方法)；
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //3.打印结果信息
        HighLevelClient.printResult(response);

    }
}
