package com.hero.mq.rocketmq.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 消息的接受者
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        //1.创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //指定Nameserver地址
        consumer.setNamesrvAddr("192.168.200.128:9876;192.168.200.129:9876");
        //3.订阅主题Topic和Tag
        consumer.subscribe("base", "*");

        //设定消费模式：
        // 集群模式，采用集群模式方式消费消息，多个消费者共同消费队列消息，每个消费者处理的消息不同
        // 广播模式，采用广播模式消费消息，每个消费者消费的消息都是相同的
        consumer.setMessageModel(MessageModel.CLUSTERING);

        //4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            //接受消息内容
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("consumeThread=" + Thread.currentThread().getName() + "," + new String(msg.getBody()));
                    int reconsumeTimes = msg.getReconsumeTimes();

                }
//                try {
//                    TimeUnit.SECONDS.sleep(60);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5.启动消费者consumer
        consumer.start();
    }
}
