package com.hero.multithreading;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 案例：演示一个Future的使用方法
 */
public class Demo22Future {
    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Integer> future = service.submit(new CallableTask());

        try {
            //等待3秒后打印
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }

    static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }
}


