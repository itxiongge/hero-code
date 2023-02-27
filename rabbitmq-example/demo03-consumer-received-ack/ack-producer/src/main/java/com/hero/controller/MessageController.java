package com.hero.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息Controller类，用于向消息队列发送消息
 */
@RestController
public class MessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 向消息队列中发送消息
     * @param exchange 交换机的名称
     * @param routingkey 路由键
     * @param msg 消息内容
     * http://127.0.0.1:8080/direct/sendMsg?exchange=order_exchange&routingkey=order.A&msg=购买苹果手机
     */
    @RequestMapping("/direct/sendMsg")
    public String sendMsg( String exchange, String routingkey, String msg){
        //执行消息发送
        rabbitTemplate.convertAndSend(exchange,routingkey,msg);
        return "消息已投递！！";
    }
}
