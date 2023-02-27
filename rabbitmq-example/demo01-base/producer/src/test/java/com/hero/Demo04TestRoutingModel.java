package com.hero;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 目标：将消息发送给交换机，通过交换机路由给指定的消息队列，通过路由键类指定
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo04TestRoutingModel {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        //向路由交换机发送1千条消息
        for (int i = 0; i < 1000; i++) {
            /**
             * 参数1：交换机名称
             * 参数2：路由键:error , info ，指定要投递的消息队列
             * 参数3：消息内容
             */
            if(i%2 == 0){
                rabbitTemplate.convertAndSend("routing_exchange","info","hello 我是小兔子【"+i+"】！");
            } else {
                rabbitTemplate.convertAndSend("routing_exchange","error","hello 我是小兔子【"+i+"】！");
            }
        }
    }
}
