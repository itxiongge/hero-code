package com.hero.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息队列order.A的接收消息的监听器
 */
@Component
@RabbitListener(queues = "order.A")
public class ConsuemrMessageListener {

    //接收消息的监听方法
    @RabbitHandler
    public void receivedMessageHandler(String msg){
        System.out.println("接收消息内容{}："+msg);
    }
}
