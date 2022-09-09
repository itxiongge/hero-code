package com.hero.jvm.classloader;

import java.util.concurrent.TimeUnit;

public class TestThread {
    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            new Thread("Thread-" + i) {
                @Override
                public void run() {
                    try {
                        String name = Thread.currentThread().getName();
                        System.out.println(name);
                        recurive(30000);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println();
                }
            }.start();
        }
    }
    public static void recurive(double d){
        if (d ==0)
            return;
        recurive(d - 1);
    }
}
