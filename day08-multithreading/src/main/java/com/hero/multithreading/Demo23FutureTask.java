package com.hero.multithreading;

import java.util.concurrent.*;

/**
 * 案例：演示FutureTask的用法
 */
public class Demo23FutureTask {

    public static void main(String[] args) {
        Demo23FutureTask.Task task = new Demo23FutureTask.Task();
        //FutureTask继承Future和Runnalbe接口
        FutureTask<Integer> integerFutureTask = new FutureTask<>(task);

        //new Thread(integerFutureTask).start();

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(integerFutureTask);

        try {
            System.out.println("task运行结果："+integerFutureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程正在计算");
            Thread.sleep(3000);
            //模拟子线程处理业务逻辑
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            return sum;
        }
    }
}


