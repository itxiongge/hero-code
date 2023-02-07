package com.hero.service;

public class UserServiceMock implements UserService {

    @Override
    public String getUsernameById(int id) {
        return "没有该用户：" + id;
    }

    @Override
    public void addUser(String username) {
        System.out.println("添加该用户失败：" + username);
    }
}
