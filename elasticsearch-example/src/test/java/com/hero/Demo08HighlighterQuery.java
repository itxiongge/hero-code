package com.hero;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

/**
 * 高亮查询
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo08HighlighterQuery {

    /**
     * 注入es的高级客户端
     */
    @Autowired
    private RestHighLevelClient client;

    /**
     * 高亮查询
     */
    @Test
    public void boolQuery() throws IOException {
        SearchRequest request = new SearchRequest().indices("hero4").types("_doc");
        /**
         * 构建请求体
         */
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //设置查询方式:matchQuery
        sourceBuilder.query(QueryBuilders.matchQuery("title","小米手机"));
        /**
         * 设置高亮信息
         */
        //高亮的构建器对象
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("title");
        sourceBuilder.highlighter(highlightBuilder);//设置高亮的构建器对象
        //设置请求体
        request.source(sourceBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //打印响应结果

        //响应对象中抽取查询结果信息
        System.out.println("took::" + response.getTook());
        System.out.println("time_out::" + response.isTimedOut());
        //获取所有的查询结果信息
        SearchHits hits = response.getHits();
        System.out.println("total::" + hits.getTotalHits());
        System.out.println("max_score::" + hits.getMaxScore());
        //打印查询响应头中的查询结果信息
        System.out.println("hits:::::>>");

        hits.forEach(hit -> {
            System.out.println(hit.getSourceAsString());
            //抽取高亮的结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            System.out.println(highlightFields);
        });
        System.out.println("<<:::::::");
    }


}
