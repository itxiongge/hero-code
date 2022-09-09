package com.hero.multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedVSVolatileVSAtomicClass {
    static long loop_count = 10000 * 10000;

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo01 d01 = new VolatileDemo01();
        VolatileDemo02 d02 = new VolatileDemo02();
        VolatileDemo03 d03 = new VolatileDemo03();

        for (int i = 0; i < 3; i++) {//synchronized
            Thread t = new Thread(d01, "d01-n" + i);
            t.start();
        }
        for (int i = 0; i < 3; i++) {//volatile
            Thread t = new Thread(d02,"d02-n" + i);
            t.start();
        }
        for (int i = 0; i < 3; i++) {//AtomicInteger
            Thread t = new Thread(d03,"d03-n" + i);
            t.start();
        }
        System.out.println("创建所有线程完毕~");
    }

    static class VolatileDemo01 implements Runnable {
        public int count;

        public void run() {

            long start = System.currentTimeMillis();
            addCount();
            long end = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "-synchronized耗时：【" + (end - start) + "】ms");

        }

        public  void addCount() {
            synchronized (this){
                for (int i = 0; i < loop_count; i++) {
                    count++;
                }
            }

        }
    }

    static class VolatileDemo02 implements Runnable {
        public volatile int count;

        public void run() {
            long start = System.currentTimeMillis();
            addCount();
            long end = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "-volatile耗时：【" + (end - start) +"】ms");
        }

        public void addCount() {
            for (int i = 0; i < loop_count; i++) {
                count++;
            }
        }
    }

    static class VolatileDemo03 implements Runnable {
        public AtomicInteger count = new AtomicInteger(0);

        public void run() {
            long start = System.currentTimeMillis();
            addCount();
            long end = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "-AtomicInteger耗时：【" + (end - start) +"】ms");
        }

        public void addCount() {
            for (int i = 0; i < loop_count; i++) {
                count.incrementAndGet();
            }
        }
    }

}
