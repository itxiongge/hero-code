package com.hero;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 消息队列五种工作模式之：简单模式
 * 特点：三个角色，生产者，消费者，消息队列
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo01SimpleQueue {

    //注入RabbitMQ与SpringBoot 整合对象
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        //向simple_queue发送消息
        rabbitTemplate.convertAndSend("simple_queue", "hello，你好大白兔子~");
    }

}
