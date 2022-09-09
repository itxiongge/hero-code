package com.hero.multithreading;

public class Demo06Volatile {
    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();

        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(demo);
            t.start();
        }

        Thread.sleep(1000);
        System.out.println("count = "+demo.count);
        //三个线程，每个线程循环count++，10000，请问，count
    }

    static class VolatileDemo implements Runnable {
        public volatile int count;


        public void run() {
            addCount();
        }

        public void addCount() {
            for (int i = 0; i < 10000; i++) {
                count++;
                //虽然对于咱们单行Java代码来说，是原子操作，但是在指令层面不是！
                //递增操作，不是原子操作
            }
        }
    }
}
