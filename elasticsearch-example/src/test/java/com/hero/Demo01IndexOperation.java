package com.hero;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 索引库操作：
 * 1.创建索引库
 * 2.查看索引库
 * 3.删除索引库
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo01IndexOperation {

    /**
     * 注入es的高级客户端
     */
    @Autowired
    private RestHighLevelClient client;

    /**
     * 创建索引库
     */
    @Test
    public void createIndex() throws IOException {
        //1.创建请求对象
        CreateIndexRequest request = new CreateIndexRequest("hero");
        //2.客户端对象发送请求，返回响应对象
        /**
         * create("当前的操作", "请求选项：请求头，默认响应对初始化100m");
         */
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        //3.打印响应对象结果信息
        System.out.println("index::"+response.index());
        System.out.println("acknowledged::"+response.isAcknowledged());
        //4.关闭客户端，释放资源
         client.close();
    }
    /**
     * 查询索引库
     */
    @Test
    public void getIndex() throws IOException {
        //1.创建请求对象，查询索引库
        GetIndexRequest request = new GetIndexRequest("hero");
        //2.客户端对象发送请求对象，返回响应对象
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        //3.打印响应结果
        System.out.println("aliases::"+response.getAliases());
        System.out.println("mappings::"+response.getMappings());
        System.out.println("settings::"+response.getSettings());
        //4.关闭客户端
        client.close();
    }
    /**
     * 查询索引库
     */
    @Test
    public void deleteIndex() throws IOException {
        //1.创建请求对象，删除索引库
        DeleteIndexRequest request = new DeleteIndexRequest("hero");
        //2.客户端对象发送请求对象，返回响应对象
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        //3.打印响应结果
        System.out.println("Acknowledged::"+response.isAcknowledged());
    }

}
