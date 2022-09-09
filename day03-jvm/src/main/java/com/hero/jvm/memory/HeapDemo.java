package com.hero.jvm.memory;

/**
 * -Xms100m -Xmx100m
 */
public class HeapDemo {
    public static void main(String[] args) {
        System.out.println("======start=========");
        try {
            Thread.sleep(1000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("========end=========");
    }
}
