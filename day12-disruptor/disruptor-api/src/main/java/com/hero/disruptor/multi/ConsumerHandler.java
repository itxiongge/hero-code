package com.hero.disruptor.multi;

import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsumerHandler implements WorkHandler<Order> {

    //每个消费者有自己的id
    private String comsumerId;

    //计数统计，多个消费者，所有的消费者总共消费了多个消息。
    private static AtomicInteger count=new AtomicInteger(0);

    private Random random=new Random();

    public ConsumerHandler(String comsumerId){
        this.comsumerId=comsumerId;
    }
    //当生产者发布一个sequence，ringbuffer中一个序号，里面生产者生产出来的消息，生产者最后publish发布序号
    //消费者会监听，如果监听到，就会ringbuffer去取出这个序号，取到里面消息
    @Override
    public void onEvent(Order event) throws Exception {
        //模拟消费者处理消息的耗时，设定1-4毫秒之间
        TimeUnit.MILLISECONDS.sleep(1*random.nextInt(5));

        System.err.println("当前消费者:"+this.comsumerId+",消费信息ID:"+event.getId());
        //count计数器增加+1，表示消费了一个消息
        count.incrementAndGet();

    }

    //返回所有消费者总共消费的消息的个数。
    public int getCount(){
        return count.get();
    }
}
