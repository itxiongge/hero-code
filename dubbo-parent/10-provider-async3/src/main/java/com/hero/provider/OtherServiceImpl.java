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
        // 异步调用耗时操作
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // 耗时操作是由CompletableFuture调用的，而不是由业务线程直接调用
            sleep();
            return "doThird()";
        });
        long endTime = System.currentTimeMillis();
        System.out.println("doThird()方法执行用时：" + (endTime - startTime));
        return future;
    }

    @Override
    public CompletableFuture<String> doFourth() {
        long startTime = System.currentTimeMillis();
        // 异步调用耗时操作
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep();
            return "doFourth()";
        });
        long endTime = System.currentTimeMillis();
        System.out.println("doFourth()方法执行用时：" + (endTime - startTime));
        return future;
    }

}
