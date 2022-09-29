package com.hero.disruptor.demo;


import com.lmax.disruptor.EventHandler;

//消费者
public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.err.println("消费者:"+ orderEvent.getValue()); //取出订单对象的价格。
    }
}
