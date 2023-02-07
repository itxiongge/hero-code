package com.hero.consumer;

import com.hero.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerCacheRunOne {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-consumer.xml");
        UserService service = (UserService) ac.getBean("userService");

        //LRU缓存条目是1000条
        System.out.println(service.hello("i==1"));
        System.out.println(service.hello("i==1"));
        System.out.println(service.hello("i==1"));
        System.out.println(service.hello("i==1"));
        System.out.println(service.hello("i==1"));
    }
}
