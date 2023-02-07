package com.hero.consumer;

import com.hero.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerMockClassRun {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-consumer.xml");
        UserService service = (UserService) ac.getBean("userService");

        // 无论是否有返回值，其结果均可进行自定义
        String username = service.getUsernameById(666);
        System.out.println("username = " + username);

        service.addUser("西门大官人");
    }
}
