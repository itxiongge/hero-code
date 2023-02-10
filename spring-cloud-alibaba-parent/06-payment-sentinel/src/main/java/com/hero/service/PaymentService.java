package com.hero.service;

import com.hero.entity.Payment;

import java.util.List;

public interface PaymentService {
    //查询支付信息
    List<Payment> listPayment();
}
