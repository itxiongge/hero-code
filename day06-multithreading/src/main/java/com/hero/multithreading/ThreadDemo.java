package com.hero.multithreading;

public class ThreadDemo {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程");
            }
        };
        Thread thread =new Thread(r);
        thread.start();//为什么run()方法也就执行了呢？
        Object o = new Object();
    }
}
