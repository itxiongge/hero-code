package com.hero.disruptor.demo;

import com.lmax.disruptor.EventFactory;

//建立一个工厂类，用于创建Event的实例（OrderEvent)
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent(); //返回空的数据对象，不是null,OrderEvent,value属性还没有赋值。
    }
}
