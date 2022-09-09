package com.hero.multithreading;

public class Demo01Ticket {

    public static void main(String[] args) {
        //创建线程任务对象
        SellTicketTask task = new SellTicketTask();
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
class SellTicketTask implements Runnable {
    //电影票100张
    private int tickets = 100;
    /*
     * 每个窗口执行相同的卖票操作
     * 窗口永远开启，所有窗口卖完100张票为止
     */
    @Override
    public void run() {
        while (true) {
            //有票 可以卖
            if (tickets > 0){
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
        }
    }
}
