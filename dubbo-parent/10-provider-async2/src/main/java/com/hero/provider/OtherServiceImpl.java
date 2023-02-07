package com.hero.provider;

import com.hero.service.OtherService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class OtherServiceImpl implements OtherService {

    // 耗时操作
    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String doFirst() {
        sleep();
        return "doFirst()";
    }

    @Override
    public String doSecond() {
        sleep();
        return "doSecond()";
    }

    @Override
    public CompletableFuture<String> doThird() {
        long startTime = System.currentTimeMillis();
        // 耗时操作仍由业务线程调用
        sleep();
        CompletableFuture<String> future =
                CompletableFuture.completedFuture("doThird()-----");
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        System.out.println("doThird()方法执行用时：" + useTime);
        return future;
    }

    @Override
    public CompletableFuture<String> doFourth() {
        long startTime = System.currentTimeMillis();
        sleep();
        CompletableFuture<String> future =
                CompletableFuture.completedFuture("doFourth()-----");
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        System.out.println("doFourth()方法执行用时：" + useTime);
        return future;
    }

}
