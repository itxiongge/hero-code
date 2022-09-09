package com.hero.multithreading;

import java.util.concurrent.CountDownLatch;

/**
 * 描述：多线程情况下重排序的现象
 * @author liuyang
 */
public class ReOrder {

    // 共享变量
    private static int x = 0;
    private static int y = 0;
    private static int a = 0;
    private static int b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        // 死循环
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            CountDownLatch latch = new CountDownLatch(3);
            Thread one = new Thread(() -> {
                try {
                    latch.countDown();
                    // 模拟同时执行
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a = 1;
                x = b;
            });
            Thread two = new Thread(() -> {
                try {
                    latch.countDown();
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                b = 1;
                y = a;
            });
            one.start();
            two.start();
            latch.countDown();
            // 等待执行完成
            one.join();
            two.join();
            String result = "第" + i + "次(" + x + "," + y + ")";
            if (x == 0 && y == 0) {
                System.out.println("出现指令重排：");
                System.out.println(result);
                break;
            } else {
                System.out.println(result);
            }
        }
    }
}
