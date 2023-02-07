package com.hero.hessian.service.impl;

import com.hero.hessian.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public String sayHello(String name) {
        System.out.println("=======User-金莲-提供服务=======");
        return "金莲Say你好：" + name;
    }

}
