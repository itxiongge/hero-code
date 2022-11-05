package com.hero;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hero.pojo.Order;
import com.hero.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

//测试分库分表
@SpringBootTest
class ShardingRuleTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void addOrder() throws Exception {
        int userId = 10;
        for (int i = 1;i <= 20; i++) {
            if (i >= 10) {
                userId = 21;
            }
            Order order = new Order();
            order.setOrderId(i);
            order.setUserId(userId);
            order.setInfo("订单信息：user_id=" + userId + ",order_id=" + i);
            boolean result = orderService.save(order);
            if (result) {
                System.out.println("订单" + i + "添加成功");
            }
        }
    }
    @Test
    public void findAll() throws Exception {
        List<Order> orderList = orderService.list();
        orderList.forEach(System.out::println);
    }

    @Test
    public void findById() throws Exception {
        Order order = orderService.getById(1);
        System.out.println(order);
    }

    @Test
    public void findByUserId() throws Exception {
        List<Order> orderList = orderService.findByUserId(21);
        orderList.forEach(System.out::println);
    }

    @Test
    public void deleteAll() throws Exception {
        orderService.remove(new QueryWrapper<Order>().lambda().gt(Order::getOrderId,1));
    }
}
