package com.hero;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
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
 * 批量操作
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo04BulkOperation {

    /**
     * 注入es的高级客户端
     */
    @Autowired
    private RestHighLevelClient client;

    /**
     * 批量新增，在一次请求中，完成多个文档的新增操作
     * 1.创建请求对象
     * 2.发送请求对象
     * 3.打印响应结果信息
     */
    @Test
    public void bulkCreate() throws IOException {
        BulkRequest request = new BulkRequest();
        //新增文档
        request.add(new IndexRequest().id("1").type("_doc").index("hero1").source(XContentType.JSON,"title","小米","price",2699));
        request.add(new IndexRequest().id("2").type("_doc").index("hero1").source(XContentType.JSON,"title","大米","price",3699));
        request.add(new IndexRequest().id("3").type("_doc").index("hero1").source(XContentType.JSON,"title","超米","price",19999));
        //发送请求，获取响应对象
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        //打印响应结果信息
        System.out.println("took::"+response.getTook());
        System.out.println("items::"+response.getItems());
    }
    //批量删除
    @Test
    public void bulkDelete() throws IOException {
        BulkRequest request = new BulkRequest();
        //新增文档
        request.add(new DeleteRequest().id("1").type("_doc").index("hero1"));
        request.add(new DeleteRequest().id("2").type("_doc").index("hero1"));
        request.add(new DeleteRequest().id("3").type("_doc").index("hero1"));
        //发送请求，获取响应对象
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        //打印响应结果信息
        System.out.println("took::"+response.getTook());
        System.out.println("items::"+response.getItems());
    }

    /**
     * 初始化查询数据
     */
    @Test
    public void initData() throws IOException {
        //批量新增操作
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().type("_doc").index("hero1").source(XContentType.JSON,"title","大米手机","images","http://image.leyou.com/12479122.jpg","price",3288,"category","手机","brand","小米"));
        request.add(new IndexRequest().type("_doc").index("hero1").source(XContentType.JSON,"title","小米手机","images","http://image.leyou.com/12479122.jpg","price",2699,"category","手机","brand","小米"));
        request.add(new IndexRequest().type("_doc").index("hero1").source(XContentType.JSON,"title","小米电视4A","images","http://image.leyou.com/12479122.jpg","price",4288,"category","手机","brand","小米"));
        request.add(new IndexRequest().type("_doc").index("hero1").source(XContentType.JSON,"title","华为手机","images", "http://image.leyou.com/12479122.jpg","price", 5288,"subtitle", "小米","category","手机","brand","小米"));
        request.add(new IndexRequest().type("_doc").index("hero1").source(XContentType.JSON,"title","apple手机","images","http://image.leyou.com/12479122.jpg","price",5899.00,"category","手机","brand","小米"));
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println("took::"+response.getTook());
        System.out.println("Items::"+response.getItems());
    }
}
