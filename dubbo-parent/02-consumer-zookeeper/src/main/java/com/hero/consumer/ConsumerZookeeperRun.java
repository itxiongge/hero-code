package com.hero.consumer;

import com.hero.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ConsumerZookeeperRun {
    public static void main(String[] args) throws IOException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-consumer.xml");
        UserService service = (UserService) ac.getBean("userService");
        for (int i = 0; i < 10; i++) {
            String hello = service.hello("西门大官人");
            System.out.println(hello);

        }
        System.in.read();
    }
}
