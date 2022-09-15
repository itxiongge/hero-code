package com.hero.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 案例：演示newFixedThreadPool出错的情况
 * -Xmx8m -Xms8m
 */
public class Demo18FixedThreadPoolOOM {

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(new SubThread());
        }
    }

}

class SubThread implements Runnable {
    @Override
    public void run() {
        try {
            //处理越慢越好，演示内存溢出
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
