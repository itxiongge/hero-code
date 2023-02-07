package com.hero.controller;

import com.hero.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping("/sayHello.do")
    public String sayHelloHandle() {
        String result = service.hello("西门大官人");
        System.out.println("消费者端接收到 = " +  result);
        return "/welcome.jsp";
    }
}


