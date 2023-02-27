package com.hero;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 消息队列五种工作模式之：工作队列模式
 * 特点：
 *   三个角色，生产者，多个消费者，消息队列
 *   消费者分担队列中的消息，每个消息只会被一个消费者处理
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo02WorkQueue {

    //注入RabbitMQ与SpringBoot 整合对象
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        //向simple_queue发送消息
        for (int i = 0; i < 1000; i++) {
            rabbitTemplate.convertAndSend("work_queue", "hello，你好大白兔子~["+i+"]");
        }
    }

}
