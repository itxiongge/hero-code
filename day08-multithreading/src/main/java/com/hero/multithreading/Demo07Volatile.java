package com.hero.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo07Volatile {
    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();

        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(demo);
            t.start();
        }

        Thread.sleep(1000);
        System.out.println("count = "+demo.count);
        //结果：30000
        //原子类如何保证原子性呢？ CAS的操作，在CPU指令层面加锁
        //原子类如何保证的可见性呢？ volatile，在CPU指令层面加锁
    }

    static class VolatileDemo implements Runnable {
        //原子类
        public AtomicInteger count = new AtomicInteger(0);

        public void run() {
            addCount();
        }

        public void addCount() {
            for (int i = 0; i < 10000; i++) {
                count.incrementAndGet();
            }
        }
    }
}
