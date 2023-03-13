package com.hero;

import com.hero.pojo.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 创建索引
 * 配置映射
 * 删除索引
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo01IndexOperation {
    //模板设计模式
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void createIndexAndPutMapping() {
        elasticsearchTemplate.createIndex(Goods.class);//创建索引
        elasticsearchTemplate.putMapping(Goods.class);//配置映射
    }
    //删除索引
    @Test
    public void deleteIndex(){
        elasticsearchTemplate.deleteIndex(Goods.class);
    }

}
