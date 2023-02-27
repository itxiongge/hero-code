package com.hero.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过Java代码创建队列，创建交换机，绑定交换机与消息队列
 */
@Configuration
public class RabbitConfiguration {

    //创建消息队列
    @Bean
    public Queue orderQueue(){
        /**
         * 参数1：队列的名称
         * 参数2：是否是持久化
         * 参数3：是否独占
         * 参数4：是否自动删除
         */
        return new Queue("order_test",true,false,false);
    }
    //创建交换机
    @Bean
    public DirectExchange exchange(){
        /**
         * 参数1：交换机的名称
         * 参数2：是否是持久化
         * 参数3：是否自动删除
         */
        return new DirectExchange("ordertwo_exchange",true,false);
    }
    //交换机绑定消息队列
    @Bean
    public Binding exchangeBindingQueue(
            Queue orderQueue
            ,DirectExchange exchange){
        /**
         * BindingBuilder.bind("队列对象").to("交换机对象").with("路由键")
         */
        return BindingBuilder.bind(orderQueue).to(exchange).with("");
    }
}
