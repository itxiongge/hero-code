package com.hero.multithreading;

public class Demo03Jmm {

    public static void main(String[] args) throws InterruptedException {

        JmmDemo demo = new JmmDemo();
        Thread t = new Thread(demo);
        t.start();

        Thread.sleep(100);

        demo.flag = false;
        System.out.println("已经修改为false");
        System.out.println(demo.flag);//false
        //已经将true --> false
    }

    static class JmmDemo implements Runnable {
        public boolean flag = true;

        public void run() {
            System.out.println("子线程执行。。。");
            while (flag) {

            }
            System.out.println("子线程结束。。。");//能不能看到子线程结束呢？。。。。。扣2，不可以扣3
        }
    }
}
