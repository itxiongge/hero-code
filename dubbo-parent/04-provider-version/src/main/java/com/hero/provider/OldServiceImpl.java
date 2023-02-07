package com.hero.provider;


import com.hero.service.UserService;

public class OldServiceImpl implements UserService {
    @Override
    public String hello(String name) {
        System.out.println("执行OldServiceImpl#hello() ");
        return "我是金莲";
    }
}
