package com.hero.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hero.pojo.Order;

import java.util.List;

public interface OrderService extends IService<Order> {

    List<Order> findByUserId(int i);
}
