package com.hero.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hero.pojo.User;

import java.util.List;

public interface UserService extends IService<User> {

    Integer addUser(User user);

    List<User> selectLikeName(String name);
}
