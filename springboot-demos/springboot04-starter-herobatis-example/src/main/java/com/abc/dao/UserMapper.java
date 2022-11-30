package com.abc.dao;

import com.abc.pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> findAll();
}
