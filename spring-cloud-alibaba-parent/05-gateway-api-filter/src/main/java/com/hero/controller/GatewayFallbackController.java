package com.hero.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GatewayFallbackController {
    //定义服务降级处理方法
    @RequestMapping("/fallback")
    public Map<String,Object> defaultfallback(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","服务超时降级");
        map.put("data",null);
        return map;
    }
}
