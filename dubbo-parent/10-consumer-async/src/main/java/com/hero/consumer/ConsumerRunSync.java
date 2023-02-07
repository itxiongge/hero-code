package com.hero.consumer;

import com.hero.service.OtherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutionException;

public class ConsumerRunSync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-consumer.xml");
        OtherService service = (OtherService) ac.getBean("otherService");

        // 记录同步调用开始时间
        long syncStart = System.currentTimeMillis();

        // 同步调用
        String first = service.doFirst();
        System.out.println("同步，doFirst()直接获取到返回值：" + first);
        String second = service.doSecond();
        System.out.println("同步，doSecond()直接获取到返回值：" + second);

        System.out.println("两个同步操作共计用时（毫秒）：" + (System.currentTimeMillis() - syncStart));//10s左右

    }
}
