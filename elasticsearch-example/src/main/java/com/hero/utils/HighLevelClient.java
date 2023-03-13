package com.hero.utils;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class HighLevelClient {
    /**
     * 打印查询响应结果
     * @param response
     */
    public static void printResult(SearchResponse response) {
        //响应对象中抽取查询结果信息
        System.out.println("took::" + response.getTook());
        System.out.println("time_out::" + response.isTimedOut());
        //获取所有的查询结果信息
        SearchHits hits = response.getHits();
        System.out.println("total::" + hits.getTotalHits());
        System.out.println("max_score::" + hits.getMaxScore());
        //打印查询响应头中的查询结果信息
        System.out.println("hits:::::>>");
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
        System.out.println("<<:::::::");
    }
}
