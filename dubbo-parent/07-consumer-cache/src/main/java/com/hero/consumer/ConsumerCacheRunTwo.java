package com.hero.consumer;

import com.hero.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerCacheRunTwo {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-consumer.xml");
        UserService service = (UserService) ac.getBean("userService");

        //LRU缓存上限是1000条，超过的缓存则失效
        for (int i=1; i<=1002; i++) {
            for (int j = 0; j < 5; j++) {//每个请求发5次，缓存数据
                service.hello("i==" + i);
            }
        }
        // 调用提供者，并将其执行结果存放到缓存，根据 LRU（最近最少使用）策略，其会将i==1的缓存结果挤出去
        // 由于缓存中已经没有了i==1的缓存，所以其会调用提供者
        for (int j = 0; j < 5; j++) {
            System.out.println(service.hello("i==1"));
        }
        // 缓存中还存在i==1000的缓存，所以会调用LRU缓存
        for (int j = 0; j < 5; j++) {
            System.out.println(service.hello("i==1000"));
        }
    }
}
