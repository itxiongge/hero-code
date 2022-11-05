package com.hero.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hero.mapper.UserMapper;
import com.hero.pojo.User;
import com.hero.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Integer addUser(User user) {
        return baseMapper.insert(user);
    }

    @Override
    public List<User> selectLikeName(String name) {
        name = "%" + name + "%";
        return baseMapper.selectLikeName(name);
    }
}
