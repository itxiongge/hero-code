package com.hero.multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
/**
 * CountDownLatch案例：6个程序猿加班
 * 当计数器的值变为0时，因await方法阻塞的线程会被唤醒，继续执行
 */
public class Demo11CountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        //计数门闩
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + "\t上完班，离开公司");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        new Thread(()->{
            try {
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName()+"\t卷王最后关灯走人");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "7").start();


    }
}
