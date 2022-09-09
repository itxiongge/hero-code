package com.hero.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo11Condition {

    private Lock reentrantLock = new ReentrantLock();
    private Condition condition1 = reentrantLock.newCondition();
    private Condition condition2 = reentrantLock.newCondition();

    public void m1() {
        reentrantLock.lock();
        try {
            System.out.println("线程 " + Thread.currentThread().getName() + " 已经进入执行等待。。。");
            condition1.await();
            System.out.println("线程 " + Thread.currentThread().getName() + " 已被唤醒，继续执行。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void m2() {
        reentrantLock.lock();
        try {
            System.out.println("线程 " + Thread.currentThread().getName() + " 已经进入执行等待。。。");
            condition1.await();
            System.out.println("线程 " + Thread.currentThread().getName() + " 已被唤醒，继续执行。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void m3() {
        reentrantLock.lock();
        try {
            System.out.println("线程 " + Thread.currentThread().getName() + " 已经进入执行等待。。。");
            condition2.await();
            System.out.println("线程 " + Thread.currentThread().getName() + " 已被唤醒，继续执行。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void m4() {
        reentrantLock.lock();
        try {
            System.out.println("线程 " + Thread.currentThread().getName() + " 已经进入发出condition1唤醒信号。。。");
            condition1.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void m5() {
        reentrantLock.lock();
        try {
            System.out.println("线程 " + Thread.currentThread().getName() + " 已经进入发出condition2唤醒信号。。。");
            condition2.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        final Demo11Condition useCondition = new Demo11Condition();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                useCondition.m1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                useCondition.m2();
            }
        }, "t2");
        Thread t3 = new Thread(new Runnable() {
            public void run() {
                useCondition.m3();
            }
        }, "t3");
        Thread t4 = new Thread(new Runnable() {
            public void run() {
                useCondition.m4();
            }
        }, "t4");
        Thread t5 = new Thread(new Runnable() {
            public void run() {
                useCondition.m5();
            }
        }, "t5");

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(2000);
        t4.start();


        Thread.sleep(2000);
        t5.start();
    }
}
