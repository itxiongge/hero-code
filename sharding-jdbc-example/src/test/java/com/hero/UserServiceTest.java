package com.hero;

import com.hero.pojo.User;
import com.hero.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserServiceTest {
    private UserService userService;
    @Before
    public void init() {
        userService = new UserService();
    }
    @Test
    public void addUser() throws Exception {
        User user = new User();
        user.setName("张翼德");
        user.setAge(26);
        user.setAddress("蜀国");
        userService.addUser(user);
    }
    @Test
    public void getUserList() throws Exception {
        List<User> userList = userService.getUserList();
        userList.forEach(System.out::println);
    }
}
