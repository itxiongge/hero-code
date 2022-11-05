package com.hero;

import com.hero.pojo.Order;
import com.hero.service.OrderService;
import org.junit.Test;

import java.util.List;

public class OrderServiceTest {
    @Test
    public void addOrder() throws Exception {
        OrderService orderService = new OrderService();
        int userId = 10;
        for (int i = 1;i <= 20; i++) {
            if (i >= 10) {
                userId = 21;
            }
            Order order = new Order();
            order.setOrderId(i);
            order.setUserId(userId);
            order.setInfo("订单信息：user_id=" + userId + ",order_id=" + i);
            boolean result = orderService.addOrderInfo(order);
            if (result) {
                System.out.println("订单" + i + "添加成功");
            }
        }
    }
    @Test
    public void findAll() throws Exception {
        OrderService orderService = new OrderService();
        List<Order> orderList = orderService.findAll();
        orderList.forEach(System.out::println);
    }

    @Test
    public void findByPage() throws Exception {
        OrderService orderService = new OrderService();
        List<Order> orderList = orderService.findByPage();
        orderList.forEach(System.out::println);
    }

    @Test
    public void findById() throws Exception {
        OrderService orderService = new OrderService();
        Order order = orderService.findById(2);
        System.out.println(order);
    }

    @Test
    public void findByUserId() throws Exception {
        OrderService orderService = new OrderService();
        List<Order> orderList = orderService.findByUserId(21);
        orderList.forEach(System.out::println);
    }

    @Test
    public void deleteAll() throws Exception {
        OrderService orderService = new OrderService();
        orderService.deleteAll();
    }
}
