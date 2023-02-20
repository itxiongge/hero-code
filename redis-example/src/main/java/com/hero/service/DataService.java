package com.hero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class DataService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String getKey(String key) {
        String value = redisTemplate.opsForValue().get(key);
        return value;
    }

}
