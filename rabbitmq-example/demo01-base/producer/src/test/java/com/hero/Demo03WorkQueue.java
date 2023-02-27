package com.hero;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 消息队列五种工作模式之：发布订阅模式
 * 特点：
 *   四个角色，生产者，多个消费者，消息队列，交换器
 *   消费者从消息队列中，接收相同的消息
 *   一般适用于商品下单
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo03WorkQueue {

    //注入RabbitMQ与SpringBoot 整合对象
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        //向fanout_exchange发送消息
        for (int i = 0; i < 100; i++) {
            /**
             * 参数1：交换机名称
             * 参数2：路由规则[没有指定规则会发送给所有的队列]
             * 参数3：消息内容
             */
            rabbitTemplate.convertAndSend("fanout_exchange","", "hello，你好大白兔子~["+i+"]");
        }
    }

}
