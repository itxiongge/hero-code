package com.hero.provider;


import com.hero.service.UserService;

public class NewServiceImpl implements UserService {
    @Override
    public String hello(String name) {
        System.out.println("执行NewServiceImpl#hello() ");
        return "我是李瓶儿";
    }
}
