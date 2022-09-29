package com.hero.disruptor.multi;

import com.lmax.disruptor.RingBuffer;

public class Producer {

    private RingBuffer<Order> ringBuffer;

    //为生产者绑定ringbuffer
    public Producer(RingBuffer<Order> ringBuffer){
        this.ringBuffer=ringBuffer;
    }

    //发送数据
    public void sendData(String uuid){
        //1.获取到可用sequence
        long sequence=ringBuffer.next();
        try{
            Order order=ringBuffer.get(sequence);
            order.setId(uuid);
        }finally {
            //发布序号
            ringBuffer.publish(sequence);
        }
    }
}
