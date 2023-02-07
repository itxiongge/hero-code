package com.hero.consumer;

import com.hero.service.OtherService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ConsumerRunAsyncTwo {
    public static void main(String[] args)
            throws ExecutionException, InterruptedException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-consumer.xml");
        OtherService service = (OtherService) ac.getBean("otherService");

        // 记录异步调用开始时间
        long asyncStart = System.currentTimeMillis();

        // 异步调用
        String result1 = service.doThird();
        System.out.println("调用结果3 = " + result1);
        Future<String> thirdFuture = RpcContext.getContext().getFuture();

        String result3 = service.doFourth();
        System.out.println("调用结果4 = " + result3);
        Future<String> fourFuture = RpcContext.getContext().getFuture();

        // 阻塞
        String result2 = thirdFuture.get();//获取接口执行结果，这个方法是阻塞（异步阻塞调用 vs 异步非阻塞调用（事件回调））
        System.out.println("调用结果3 = " + result2);
        String result4 = fourFuture.get();
        System.out.println("调用结果4 = " + result4);

        long useTime = System.currentTimeMillis() - asyncStart;
        System.out.println("获取到异步调用结果共计用时：" + useTime);//时间大约是5s
    }
}
