package com.hero.config;

import com.hero.listener.CustomAckConsumerListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息监听器的配置类：将消息队列中order.A队列，绑定到自定义监听器上
 */
@Configuration
public class ListenerConfiguration {


    //自定义监听器对象的适配器对象
    @Bean
    public MessageListenerAdapter messageListenerAdapter(CustomAckConsumerListener customAckConsumerListener){
        return new MessageListenerAdapter(customAckConsumerListener);
    }

    /**
     * 监听器的容器对象
     * 设置连接工厂对象ConnectionFactory == SqlSessionFactory
     * 设置自定义监听器对象的适配器对象MessageListenerAdapter
     * 设置绑定的消息队列order.A
     * 设置手动签收模式【重要】acknowledgedMode  手动签收MANUAL
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(
            MessageListenerAdapter messageListenerAdapter
            , ConnectionFactory connectionFactory){
        //监听器对象的容器对象
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        //设置自定义监听器对象的适配器对象MessageListenerAdapter
        container.setMessageListener(messageListenerAdapter);
        //设置连接工厂对象ConnectionFactory
        container.setConnectionFactory(connectionFactory);
        //设置绑定的消息队列order.A
        container.setQueueNames("order.A");
        //设置手动签收模式【重要】MANUAL手动签收，none自动签收，auto出现异常之后签收
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置消费端限流，限制每次拉取1条消息,默认的每次拉取，250条
        container.setPrefetchCount(1);
        return container;
    }
}
