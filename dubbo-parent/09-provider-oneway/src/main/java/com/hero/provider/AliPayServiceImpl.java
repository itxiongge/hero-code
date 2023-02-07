package com.hero.provider;


import com.hero.service.UserService;

public class AliPayServiceImpl implements UserService {
    @Override
    public String hello(String name) {
        System.out.println("使用【支付宝】付款");
        return "支付宝支付成功";
    }
}
