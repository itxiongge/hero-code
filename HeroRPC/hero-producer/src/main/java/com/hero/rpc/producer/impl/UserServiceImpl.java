package com.hero.rpc.producer.impl;

import com.hero.rpc.producer.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public String findById() {
        return "user{id=1,username=xiongge}";
    }
}
