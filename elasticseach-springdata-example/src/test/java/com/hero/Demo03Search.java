package com.hero;

import com.hero.dao.GoodsDao;
import com.hero.pojo.Goods;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 文档的增删改查
 * match查询
 * matchAll查询
 * multiMatch查询
 * 分页查询
 * 排序
 * 过滤
 * 模糊查询
 * 范围查询
 * 布尔查询
 * ....
 * 高亮查询
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo03Search {
    //模板设计模式

    @Autowired
    private GoodsDao goodsDao;

    // match查询
    @Test
    public void matchQuery(){
        Iterable<Goods> goods = goodsDao.search(QueryBuilders.matchQuery("title", "小米手机"));
        for (Goods good : goods) {
            System.out.println(good.toString());
        }
    }
    // term查询
    @Test
    public void termQuery(){
        Iterable<Goods> goods = goodsDao.search(QueryBuilders.termQuery("title","小米"));
        for (Goods good : goods) {
            System.out.println(good.toString());
        }
    }

    //TODO matchAll查询

    //TODO multiMatch查询

    //TODO 模糊查询

    //TODO 范围查询

    //TODO 布尔查询
}
