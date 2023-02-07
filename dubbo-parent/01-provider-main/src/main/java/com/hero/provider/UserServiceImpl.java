package com.hero.provider;


import com.hero.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public String hello(String name) {
        System.out.println(name + "你好，我是金莲！");
        return "金莲say：你好大官人！";
    }
}
