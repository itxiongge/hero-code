package com.hero.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web")
public class HelloController {

    @RequestMapping("/sayHello")
    public String sayHello(){
        return "hello hero!!!";
    }
}
