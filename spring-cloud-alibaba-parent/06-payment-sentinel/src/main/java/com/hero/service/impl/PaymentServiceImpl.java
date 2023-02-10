package com.hero.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.hero.entity.Payment;
import com.hero.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @SentinelResource(value = "qpsFlowRule" ,fallback = "listPaymentFallback")
    @Override
    public List<Payment> listPayment() {
        Payment payment = new Payment(1, "列表查询成功");
        List<Payment> list = new ArrayList<>();
        list.add(payment);
        return list;
    }

    //指定服务降级方法
    public List<Payment> listPaymentFallback() {
        List<Payment> list = new ArrayList<>();
        Payment payment = Payment.builder().message("no any").build();
        list.add(payment);
        return list;
    }
}
