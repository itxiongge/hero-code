package com.hero.multithreading;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * 案例：演示newScheduledThreadPool
 */
public class Demo21ScheduledThreadPool {

    public static void main(String[] args) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);
        //延迟5秒运行
        //threadPool.schedule(new Task(), 5, TimeUnit.SECONDS);
        //先延迟1秒运行，然后每隔3秒运行一次
        threadPool.scheduleAtFixedRate(new Task(), 1, 3, TimeUnit.SECONDS);
    }
}
