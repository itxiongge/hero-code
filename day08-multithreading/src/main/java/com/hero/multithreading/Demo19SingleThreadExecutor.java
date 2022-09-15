package com.hero.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo19SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Task());
        }
    }
}
