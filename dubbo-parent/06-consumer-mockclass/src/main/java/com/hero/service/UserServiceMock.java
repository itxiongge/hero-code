package com.hero.service;

//降级类业务代码
public class UserServiceMock implements UserService {

    @Override
    public String getUsernameById(int id) {
        return "无法查询id为【" + id+"】的用户";
    }

    @Override
    public void addUser(String username) {
        System.out.println("添加【"+username+"】用户失败" );
    }
}
