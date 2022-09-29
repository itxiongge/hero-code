package com.hero.disruptor.demo;

//订单对象，生产者要生产订单对象，消费者消费订单对象
public class OrderEvent {

    private long value; //订单的价格

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
