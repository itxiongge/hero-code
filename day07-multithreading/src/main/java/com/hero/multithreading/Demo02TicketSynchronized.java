package com.hero.multithreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo02TicketSynchronized {

    public static void main(String[] args) {
        //创建线程任务对象
        SellTicketTaskSynchronized task = new SellTicketTaskSynchronized();
        //创建三个窗口对象
        Thread t1 = new Thread(task, "窗口1");
        Thread t2 = new Thread(task, "窗口2");
        Thread t3 = new Thread(task, "窗口3");

        //同时卖票
        t1.start();
        t2.start();
        t3.start();
    }


}

class SellTicketTaskSynchronized implements Runnable {
    //电影票100张
    private int tickets = 100;
    //private final Object lock = new Object();//锁对象，可以是任意类型数据
    private Lock lock = new ReentrantLock();//可重入锁

    /*
     * 每个窗口执行相同的卖票操作
     * 窗口永远开启，所有窗口卖完100张票为止
     */
    @Override
    public  void run() {
        while (true) {
            lock.lock();

            //synchronized (lock) {
                //有票 可以卖
                if (tickets > 0) {
                    //模拟出票时间：使用sleep模拟一下出票时间
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //获取当前线程对象的名字
                    String name = Thread.currentThread().getName();
                    System.out.println(name + "-正在卖:" + tickets--);
                }
            //}
            //需要同步操作的代码
            lock.unlock();
        }
    }
}
