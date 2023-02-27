package com.hero.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 五种工作模式之一：发布订阅模式，消息队列监听器
 */
@Component//注入Spring的容器
@RabbitListener(queues = "fanout_queue2")
public class FanoutQueue2Listener {

    //监听队列消息的处理方法
    @RabbitHandler
    public void simpleHandler(String msg){
        System.out.println("发布订阅模式，监听器2 msg = " + msg);//
    }
}
