package com.hero.controller;

import com.hero.entity.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/info")
public class SomeController {

    @GetMapping("/param")
    public String payment(String color) {
        return "Color: " + color;
    }


    @RequestMapping("header")
    public String headerHandler(HttpServletRequest request){
        String header = request.getHeader("x-Request-red");
        return "x-Request-red：" + header;
    }

    //获取当前系统时间
    @RequestMapping("time")
    public String time(HttpServletRequest request){
        return "time：" + System.currentTimeMillis();
    }
}
