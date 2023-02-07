package com.hero.provider;


import com.hero.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public String hello(String name) {
        System.out.println("执行【第三个】金莲的hello() ");
        return "我是金莲03号";
    }
}
