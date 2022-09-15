package com.hero.multithreading;

import java.util.Collections;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 案例：Tony仨小哥洗剪吹
 * 演示多线程之间按顺序调用，实现A->B->C
 * 三个线程Tony要求如下：
 *   tony雄雄-洗头，tony超超-理发，tony麦麦-吹干
 *   。。。
 *   tony雄雄-洗头，tony超超-理发，tony麦麦-吹干
 *  依次来10轮
 */
public class Demo14ConditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareData.wash();
            }
        }, "tony-雄雄").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareData.cut();
            }
        }, "tony-超超").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                shareData.cook();
            }
        }, "tony-麦麦").start();

    }
}
class ShareData {
    private volatile int number = 1; //tony-雄雄:1, tony-超超:2, tony-麦麦:3

    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition(); //number == 1
    private Condition c2 = lock.newCondition(); //number == 2
    private Condition c3 = lock.newCondition(); //number == 3

    /**
     * A线程每一轮要执行的操作
     */
    public void wash() {
        lock.lock();
        try{
            //判断
            while(number != 1){
                c1.await();//阻塞
            }
            //模拟线程执行的任务
            System.out.println(Thread.currentThread().getName()+"-洗头");
            //通知
            number = 2;
            c2.signal();//唤醒了超超
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * B线程每一轮要执行的操作
     */
    public void cut() {
        lock.lock();
        try{
            //判断
            while(number != 2){
                c2.await();//阻塞
            }
            //模拟线程执行的任务
            System.out.println(Thread.currentThread().getName()+"-理发");
            //通知
            number = 3;
            c3.signal();//唤醒3
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void cook() {
        lock.lock();
        try{
            //判断
            while(number != 3){
                c3.await();
            }
            //模拟线程执行的任务
            System.out.println(Thread.currentThread().getName()+"-吹干");
            //通知
            number = 1;
            c1.signal();//唤醒雄雄
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
