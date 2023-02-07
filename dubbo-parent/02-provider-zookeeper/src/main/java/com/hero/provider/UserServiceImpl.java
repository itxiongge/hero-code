package com.hero.provider;


import com.hero.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public String hello(String name) {
        System.out.println("你好，我是金莲！name=" + name);
        return "金莲say：" + name;
    }
}
