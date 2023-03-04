package com.hero.mq.rocketmq.delay;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

public class Producer {

    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //指定Nameserver地址
        producer.setNamesrvAddr("192.168.200.128:9876;192.168.200.129:9876");
        //启动producer
        producer.start();
        for (int i = 0; i < 4; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            Message msg = new Message("DelayTopic", "Tag1", ("Hello Hero" + i).getBytes());
            //设定延迟时间（"1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"）
            msg.setDelayTimeLevel(2);
            //5.发送消息
            SendResult result = producer.send(msg);
            SendStatus status = result.getSendStatus();//发送状态
            System.out.println("发送结果:" + result);
            System.out.println("发送状态:" + status);
        }
        //6.关闭生产者producer
        producer.shutdown();
    }
}
