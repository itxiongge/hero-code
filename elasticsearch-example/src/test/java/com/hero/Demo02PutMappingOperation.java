package com.hero;

import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 1.配置映射
 * 2.查看映射
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo02PutMappingOperation {

    /**
     * 注入es的高级客户端
     */
    @Autowired
    private RestHighLevelClient client;

    /**
     * 配置映射
     */
    @Test
    public void putMapping() throws IOException {
        //1.创建请求对象，配置映射
        PutMappingRequest request = new PutMappingRequest("hero2");//设置索引库
        //构建json对象的工厂对象
        XContentBuilder jsonBuilder = XContentFactory.jsonBuilder();
        jsonBuilder.startObject()
                .startObject("properties")
                .startObject("title")
                .field("type","text").field("analyzer","ik_max_word")
                .endObject()
                .startObject("subtitle")
                .field("type","text").field("analyzer","ik_max_word")
                .endObject()
                .startObject("category")
                .field("type","keyword")
                .endObject()
                .startObject("brand")
                .field("type","keyword")
                .endObject()
                .startObject("price")
                .field("type","float")
                .endObject()
                .startObject("images")
                .field("type","keyword").field("index",false)
                .endObject()
                .endObject()
                .endObject();
        //设置请求体
        request.source(jsonBuilder);
        //2.客户端发送请求对象，获取响应对象
        AcknowledgedResponse response = client.indices().putMapping(request, RequestOptions.DEFAULT);
        //3.打印响应结果信息
        System.out.println("acknowledge：："+response.isAcknowledged());
    }
    /**
     * 查询映射
     */
    @Test
    public void getMapping() throws IOException {
        //1.创建请求对象，查询映射
        GetMappingsRequest request = new GetMappingsRequest();
        //设置索引库
        request.indices("hero1");
        //2.客户端发送请求对象，获取响应对象
        GetMappingsResponse response = client.indices().getMapping(request, RequestOptions.DEFAULT);
        //3.打印响应结果信息
        System.out.println("mappings::"+response.mappings().get("hero1").getSourceAsMap());
    }
}
