package com.hero.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hero.mapper.OrderMapper;
import com.hero.pojo.Order;
import com.hero.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Override
    public List<Order> findByUserId(int i) {
        return baseMapper.findByUserId(i);
    }
}
