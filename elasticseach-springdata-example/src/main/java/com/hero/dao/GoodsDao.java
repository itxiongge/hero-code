package com.hero.dao;

import com.hero.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * ElasticSearch的持久层的接口
 * 继承ElasticSearch的公共的持久层的接口，里边实现了所有的常见操作
 */
public interface GoodsDao extends ElasticsearchRepository<Goods,Long> {
    //根据商品标题，及商品的价格来查询所有商品信息
    List<Goods> findAllByTitleAndPrice(String title,Double price);
}
