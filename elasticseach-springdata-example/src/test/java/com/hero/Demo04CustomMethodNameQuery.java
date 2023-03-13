package com.hero;

import com.hero.dao.GoodsDao;
import com.hero.pojo.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 自定义方法名称查询：根据商品标题，及商品的价格来查询所有商品信息
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo04CustomMethodNameQuery {
    //模板设计模式

    @Autowired
    private GoodsDao goodsDao;

    // match查询
    @Test
    public void matchQuery(){
        List<Goods> goods = goodsDao.findAllByTitleAndPrice("小米", 129.0);
        for (Goods good : goods) {
            System.out.println(good.toString());
        }
    }

}
