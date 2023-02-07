package com.hero.consumer;

import com.hero.service.OtherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutionException;

public class ConsumerRunAsyncOne {
    public static void main(String[] args)
            throws ExecutionException, InterruptedException {
        ApplicationContext ac =
                new ClassPathXmlApplicationContext("spring-consumer.xml");
        OtherService service = (OtherService) ac.getBean("otherService");

        // 记录异步调用开始时间
        long asyncStart = System.currentTimeMillis();
        // 异步调用
        service.doThird();
        service.doFourth();

        long syncInvokeTime = System.currentTimeMillis() - asyncStart;
        System.out.println("两个异步调用共计用时（毫秒）：" + syncInvokeTime);//耗时大约main方法执行的时间
    }
}
