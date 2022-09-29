package com.hero.disruptor.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestMultiDisruptor {

    public static void main(String[] args) throws InterruptedException {
        //1.创建RingBuffer，Disruptor包含RingBuffer
        RingBuffer<Order> ringBuffer = RingBuffer.create(
                ProducerType.MULTI, //多生产者
                new EventFactory<Order>() {
                    @Override
                    public Order newInstance() {
                        return new Order();
                    }
                },
                1024 * 1024,
                new YieldingWaitStrategy());

        //2.创建ringBuffer屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        //3.创建多个消费者数组
        ConsumerHandler[] consumers = new ConsumerHandler[10];

        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new ConsumerHandler("C" + i);
        }

        //4.构建多消费者工作池
        WorkerPool<Order> workerPool = new WorkerPool<Order>(
                ringBuffer,
                sequenceBarrier,
                new EventExceptionHandler(),
                consumers);

        //5.设置多个消费者的sequence序号，用于单独统计消费者的消费进度。消费进度让RingBuffer知道
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        //6.启动workPool
        workerPool.start(Executors.newFixedThreadPool(5)); //在实际开发，自定义线程池。

        //要生产100生产者，每个生产者发送100个数据,投递10000
        final CountDownLatch latch = new CountDownLatch(1);

        //设置100个生产者向ringbuffer中去投递数据
        for (int i = 0; i < 100; i++) {
            final Producer producer = new Producer(ringBuffer);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //每次一个生产者创建后就处理等待。先创建100个生产者，创建完100个生产者后再去发送数据。
                        latch.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //每个生产者投递100个数据
                    for (int j = 0; j < 100; j++) {
                        producer.sendData(UUID.randomUUID().toString());
                    }
                }
            }).start();
        }
        //把所有线程都创建完
        Thread.sleep(2000);
        //唤醒，开始运行100个线程
        latch.countDown();
        //休眠10s，让生产者将100次循环走完
        TimeUnit.SECONDS.sleep(10);

        System.err.println("任务总数:" + consumers[0].getCount());

    }

    static class EventExceptionHandler implements ExceptionHandler<Order> {
        //消费时出现异常
        @Override
        public void handleEventException(Throwable throwable, long l, Order order) {

        }

        //启动时出现异常
        @Override
        public void handleOnStartException(Throwable throwable) {

        }

        //停止时出现异常
        @Override
        public void handleOnShutdownException(Throwable throwable) {

        }
    }
}
