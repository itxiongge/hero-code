package com.hero.provider;


import com.hero.service.UserService;

public class WxPayServiceImpl implements UserService {
    @Override
    public String hello(String name) {
        System.out.println("使用【微信】付款");
        return "微信支付成功";
    }
}
