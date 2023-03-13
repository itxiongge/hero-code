package com.hero;

import com.hero.dao.GoodsDao;
import com.hero.pojo.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 文档的增删改查
 *
 * 文档新增
 * 批量新增
 * 文档修改
 * 文档删除
 * 批量删除
 * 根据id查询
 * 查询所有
 *
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
public class Demo02DocDRUD {
    //模板设计模式

    @Autowired
    private GoodsDao goodsDao;

    //文档新增
    @Test
    public void save(){
        //创建一个商品的对象
        Goods goods = Goods.builder().id(1L).title("小米手机").build();
        goodsDao.save(goods);
    }
    //批量新增
    @Test
    public void saveAll(){
        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Goods goods = Goods.builder().id((long) i).title("小米手机").price((double) i).brand("小米手机").category("手机").images("http://127.0.0.1:9300/img/123.jpg").build();
            goodsList.add(goods);
        }
        goodsDao.saveAll(goodsList);
    }
    //文档修改
    @Test
    public void update(){
        //创建一个商品的对象
        Goods goods = Goods.builder().id(1L).title("大米手机").build();
        goodsDao.save(goods);
    }
    //* 文档删除
    @Test
    public void delete(){
        //创建一个商品的对象
        goodsDao.deleteById(1L);
    }
    //* 批量删除
    @Test
    public void deleteAll(){
        //创建一个商品的对象
        goodsDao.deleteAll();
    }
    //根据id查询
    @Test
    public void findById(){
        //创建一个商品的对象
        Optional<Goods> goods = goodsDao.findById(2L);
        Goods goods1 = goods.get();
        System.out.println(goods1);
    }
    //* 查询所有
    @Test
    public void findAll(){
        Iterable<Goods> goods = goodsDao.findAll();
        System.out.println(goods);
    }
}
