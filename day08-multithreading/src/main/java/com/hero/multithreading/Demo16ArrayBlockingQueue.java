package com.hero.multithreading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 案例：有10个面试者，只有1个面试官，大厅有3个位子让面试者休息，每个人面试时间10秒，模拟所有人面试的场景。
 */
public class Demo16ArrayBlockingQueue {

    static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(3);

    public static void main(String[] args) {
        Interviewer r1 = new Interviewer(queue);//面试官
        Engineers e2 = new Engineers(queue);//程序员们
        new Thread(r1).start();
        new Thread(e2).start();
    }
}

class Interviewer implements Runnable {

    BlockingQueue<String> queue;

    public Interviewer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("面试官：我准备好了，可以开始面试");
        String msg;
        try {
            while(!(msg = queue.take()).equals("stop")){
                System.out.println(msg + " 面试+开始...");
                TimeUnit.SECONDS.sleep(10);//面试10s
                System.out.println(msg + " 面试-结束...");
            }
            System.out.println("所有候选人都结束了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Engineers implements Runnable {

    BlockingQueue<String> queue;

    public Engineers(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            String candidate = "程序员" + i;
            try {
                queue.put(candidate);
                System.out.println(candidate+" 就坐=等待面试~");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            queue.put("stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
