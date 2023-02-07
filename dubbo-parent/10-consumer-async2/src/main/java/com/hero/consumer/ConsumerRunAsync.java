package com.hero.consumer;

import com.hero.service.OtherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ConsumerRunAsync {
    public static void main(String[] args)
            throws ExecutionException, InterruptedException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-consumer.xml");
        OtherService service = (OtherService) ac.getBean("otherService");

        // 记录异步调用开始时间
        long asyncStart = System.currentTimeMillis();

        // 异步调用
        CompletableFuture<String> doThirdFuture = service.doThird();
        CompletableFuture<String> doFourthFuture = service.doFourth();

        long syncInvokeTime = System.currentTimeMillis() - asyncStart;
        System.out.println("两个异步调用共计用时（毫秒）：" + syncInvokeTime);

        // 回调方法
        doThirdFuture.whenComplete((result, throwable) -> {
            if(throwable != null) {
                throwable.printStackTrace();
            } else {
                System.out.println("异步调用提供者的doThird()返回值：" + result);
            }
        });

        doFourthFuture.whenComplete((result, throwable) -> {
            if(throwable != null) {
                throwable.printStackTrace();
            } else {
                System.out.println("异步调用提供者的doFourth()返回值：" + result);
            }
        });

        long getResultTime = System.currentTimeMillis() - asyncStart;
        System.out.println("=============（毫秒）：" + getResultTime);
    }
}
