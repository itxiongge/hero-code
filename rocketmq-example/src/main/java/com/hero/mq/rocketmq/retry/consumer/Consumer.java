package com.hero.mq.rocketmq.retry.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 消息的接受者
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        //创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //指定Nameserver地址
        consumer.setNamesrvAddr("192.168.200.128:9876;192.168.200.129:9876");
        //订阅主题Topic和Tag
        consumer.subscribe("retryTopic", "*");
//        // 配置最大消息重试次数为 20 次
        consumer.setMaxReconsumeTimes(1);

        //设定消费模式：集群模式
        consumer.setMessageModel(MessageModel.CLUSTERING);

        //设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            //接受消息内容
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    //doSth 执行业务逻辑
                    System.out.println("consumeThread=" + Thread.currentThread().getName() + "," + new String(msg.getBody()));
                    int reconsumeTimes = msg.getReconsumeTimes();
                    System.out.println("重试次数 = " + reconsumeTimes);
                }
                //方式1：返回 Action.RECONSUME_LATER，消息将重试
//                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                //方式2：返回 null，消息将重试
//                 return null;
                //方式3：直接抛出异常， 消息将重试
//                throw new RuntimeException("Consumer Message exception");
                //成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5.启动消费者consumer
        consumer.start();
    }
}
