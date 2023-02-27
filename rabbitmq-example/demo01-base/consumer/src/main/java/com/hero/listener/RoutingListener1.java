package com.hero.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 路由模式：消息队列接收监听器1，接收来自路由模式发送的消息
 */
@Component
@RabbitListener(queues = "routing_queue1")
public class RoutingListener1 {

    @RabbitHandler
    public void routingHandler(String msg){
        System.out.println("=====路由模式消息接收监听器【1】=====>"+msg);
    }
}

