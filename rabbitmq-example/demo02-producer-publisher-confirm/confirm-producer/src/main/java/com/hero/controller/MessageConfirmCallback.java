package com.hero.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 生产者消息发送确认回调类，在当前类中写回调方法
 */
@Component
public class MessageConfirmCallback implements RabbitTemplate.ConfirmCallback
        ,RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * @PostConstruct 注解的作用：创建当前MessageConfirmCallback对象之后，执行操作，将回调方法设置给模板对象
     * Post代表是后，Construct代表构造函数，
     * pre代表前
     */
    @PostConstruct
    public void init(){
        //设置确认的回调方法
        rabbitTemplate.setConfirmCallback(this::confirm);
        //设置发送消息的回退方法
        rabbitTemplate.setReturnCallback(this::returnedMessage);
    }


    /**
     * 消息发送之后，回调的方法
     * @param correlationData 封装消息相关数据类
     * @param ack (success) 消息是否发送成功，true代表发送成功，如果是false代表发送失败
     * @param cause 如果发送失败了，失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack){
            System.out.println("消息成功进入了交换机");
        } else {
            System.out.println("消息成功投递失败！失败原因："+cause);
        }
    }

    /**
     * 消息发送之后，交换机路由到消息队列失败，回调此方法
     * @param message 发送的消息内容
     * @param replyCode 错误状态码
     * @param replyText 错误原因
     * @param exchange 交换机名称
     * @param routingKey 路由键
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("<<<<<<<<<<<<<发送消息失败，交换机路由到队列异常>>>>>>>>>>>");
        System.out.println("消息内容："+message.toString());
        System.out.println("错误状态码："+replyCode);
        System.out.println("错误原因："+replyText);
        System.out.println("交换机名称："+exchange);
        System.out.println("路由键："+routingKey);
        System.out.println("<<<<<<<<<<<<<：END：>>>>>>>>>>>");
    }
}
