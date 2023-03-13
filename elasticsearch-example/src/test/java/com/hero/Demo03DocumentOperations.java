package com.hero;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hero.pojo.Goods;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 文档的操作
 * 1.新增文档
 * 2.根据id查询文档
 * 3.修改文档
 * 4.删除文档
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo03DocumentOperations {

    /**
     * 注入es的高级客户端
     */
    @Autowired
    private RestHighLevelClient client;

    /**
     * 新增文档
     */
    @Test
    public void createDoc() throws IOException {
        //1.创建请求对象
        IndexRequest request = new IndexRequest();
        //设置索引库
        request.index("hero1");
        //设置类型
        request.type("_doc");
        //设置主键id
        request.id("1");
        //构建请求体
        Goods goods = Goods.builder().id(1L).title("小米手机").subtitle("小米").category("手机").build();
        //对象转换为json字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String goodsStr = objectMapper.writeValueAsString(goods);
        //设置请求体
        request.source(goodsStr, XContentType.JSON);
        //客户端发送请求，获取响应对象
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        //打印结果信息
        System.out.println("_index::"+response.getIndex());
        System.out.println("_type::"+response.getType());
        System.out.println("_id::"+response.getId());
        System.out.println("result::"+response.getResult());
    }
    /**
     * 根据id查询文档
     */
    @Test
    public void getDoc() throws IOException {
        //创建查询的请求对象
        GetRequest request = new GetRequest();
        //设置索引库
        request.index("hero1");
        //设置类型
        request.type("_doc");
        //设置文档id
        request.id("1");
        //执行请求发送，获取响应对象
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        //打印响应结果信息
        System.out.println("_index::"+response.getIndex());
        System.out.println("_type::"+response.getType());
        System.out.println("_id::"+response.getId());
        System.out.println("source::"+response.getSourceAsString());
    }

    /**
     * 修改文档
     */
    @Test
    public void updateDoc() throws IOException {
        //1.创建请求对象
        UpdateRequest request = new UpdateRequest();
        //设置索引库
        request.index("hero1");
        //设置类型
        request.type("_doc");
        //设置主键id
        request.id("1");
        //构建请求体
        Goods goods = Goods.builder().id(1L).title("大米手机").subtitle("抄米").category("手机").build();
        //对象转换为json字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String goodsStr = objectMapper.writeValueAsString(goods);
        //设置请求体
        request.doc(goodsStr, XContentType.JSON);
        //客户端发送请求，获取响应对象
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        //打印结果信息
        System.out.println("_index::"+response.getIndex());
        System.out.println("_type::"+response.getType());
        System.out.println("_id::"+response.getId());
        System.out.println("result::"+response.getResult());
    }
    /**
     * 删除文档
     */
    @Test
    public void deleteDoc() throws IOException {
        //1.创建请求对象
        DeleteRequest request = new DeleteRequest();
        //设置索引库
        request.index("hero1");
        //设置类型
        request.type("_doc");
        //设置主键id
        request.id("1");

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        //打印结果信息
        System.out.println("_index::"+response.getIndex());
        System.out.println("_type::"+response.getType());
        System.out.println("_id::"+response.getId());
        System.out.println("result::"+response.getResult());
    }
}
