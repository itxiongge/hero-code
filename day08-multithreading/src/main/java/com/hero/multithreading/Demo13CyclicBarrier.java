package com.hero.multithreading;

import java.util.concurrent.CyclicBarrier;

/**
 * 案例：集齐7龙珠召唤神龙
 */
public class Demo13CyclicBarrier {
    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("======召唤神龙");
        });

        for (int i = 1; i <= 14; i++) {
            final int tempInt = i;
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + "\t收集到第" + tempInt + "颗龙珠");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + "\t第" + tempInt + "颗龙珠飞走了");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Thread-"+String.valueOf(i)).start();
        }
    }
}
