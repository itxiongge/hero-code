package com.hero.disruptor.demo;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestDisruptor {
    public static void main(String[] args) {
        //做一些准备工作
        OrderEventFactory orderEventFactory=new OrderEventFactory();
        int ringBufferSize=8;
        //获取CPU的处理器数量
        int nThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor=Executors.newFixedThreadPool(nThreads);
        /*
          1.eventFactory:消息(event)工厂对象
          2.ringBufferSize: 容器的长度
          3.executor:线程池，建议使用自定义的线程池，线程上限。
          4.ProducerType:单生产者或多生产者
          5.waitStrategy:等待策略
         */

        //1.实例化disruptor对象
        /**
         * 参数01-orderEventFactory
         * 参数02-ringBufferSize
         * 参数03-executor
         * 参数04-ProducerType.SINGLE
         * 参数05-阻塞等待策略
         */
        Disruptor<OrderEvent> disruptor=new Disruptor<OrderEvent>(orderEventFactory,
                ringBufferSize,
                executor,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());

        //2.添加消费者的监听（去构建disruptor与消费者的一个关联关系）
        disruptor.handleEventsWith(new OrderEventHandler());

        //3.启动disruptor
        disruptor.start();

        //4.取到容器后通过生产者去生产消息
        //获取实际存储数据的容器RingBuffer
        RingBuffer<OrderEvent> ringBuffer=disruptor.getRingBuffer();

        //生产者
        OrderEventProducer producer=new OrderEventProducer(ringBuffer);

        //先初始化ByteBuffer长度为8个字节
        ByteBuffer bb=ByteBuffer.allocate(8);
        //生产10个orderEvent->value->i 0-9
        for(long i=0;i<100;i++){
            bb.putLong(0,i);
            producer.sendData(bb);
        }

        disruptor.shutdown();
        executor.shutdown();
    }
}
