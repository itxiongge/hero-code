package com.hero.multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo09ReentrantLock {

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();

        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(demo);
            t.start();
        }

        Thread.sleep(1000);
        System.out.println("count = "+demo.count);
    }

    static class VolatileDemo implements Runnable {
        public int count = 0;
        public Lock lock = new ReentrantLock();

        public void run() {
            addCount();
        }

        public void addCount() {
            lock.lock();//入口，加锁
            for (int i = 0; i < 10000; i++) {
                count++;
            }
            lock.unlock();
        }
    }
}
