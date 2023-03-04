package com.hero.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class RocketmqDelayMessageTest {

    public static final String ROCKETMQ_SERVER = "192.168.56.204:9876;192.168.56.204:9877";
    public static final String TOPIC_TEST = "TopicDelayed";

    //Producer端发送同步消息
    @Test
    public void producerDelayed() throws Exception {
        // 实例化一个生产者来产生延时消息
        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");

        producer.setNamesrvAddr(ROCKETMQ_SERVER);
             // 启动生产者
            producer.start();
            int totalMessagesToSend = 4;
            for (int i = 0; i < totalMessagesToSend; i++) {
                Message message = new Message(TOPIC_TEST, ("Hero " + i).getBytes());
                // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
                message.setDelayTimeLevel(3);
                 // 发送消息
                producer.send(message);
                log.info("发送消息： {}", i);
            }
            // 关闭生产者
            producer.shutdown();
    }

    @Test
    public void consumerDelayed() throws Exception {

        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ExampleConsumer");
        consumer.setNamesrvAddr(ROCKETMQ_SERVER);
        // 订阅Topics
        consumer.subscribe(TOPIC_TEST, "*");
        // 注册消息监听者
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : messages) {
                    // Print approximate delay time period
//                    System.out.println("Receive msg[msgId=" + msg.getMsgId() + "]  延迟：" + (System.currentTimeMillis() - msg.getStoreTimestamp()) + " ms");

                    String content = new String(msg.getBody());
                    log.info("收到消息：{}", msg.getMsgId() + " " + msg.getTopic() + " " + msg.getTags() + " " + content);

                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者
        consumer.start();
        LockSupport.parkNanos(Integer.MAX_VALUE * 1000L * 1000L);

    }

}
