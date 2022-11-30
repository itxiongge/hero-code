package com.hero.service;

import java.util.List;

//不写@Service的注入的注解
public class UserServiceImpl implements UserService {
    @Override
    public List findAll() {
        System.out.println("==假装我是一个service层的实现类方法==");
        return null;
    }
}
