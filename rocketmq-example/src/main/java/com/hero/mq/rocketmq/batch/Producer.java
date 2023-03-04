package com.hero.mq.rocketmq.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Producer {

    public static void main(String[] args) throws Exception {
        //创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //指定Nameserver地址
        producer.setNamesrvAddr("192.168.200.128:9876;192.168.200.129:9876");
        //启动producer
        producer.start();
        List<Message> msgs = new ArrayList<Message>();
        //4.创建消息对象，指定主题Topic、Tag和消息体
        /**
         * 参数一：消息主题Topic
         * 参数二：消息Tag
         * 参数三：消息内容
         */
        Message msg1 = new Message("BatchTopic", "Tag1", ("Hello Hero" + 1).getBytes());
        Message msg2 = new Message("BatchTopic", "Tag1", ("Hello Hero" + 2).getBytes());
        Message msg3 = new Message("BatchTopic", "Tag1", ("Hello Hero" + 3).getBytes());
        msgs.add(msg1);
        msgs.add(msg2);
        msgs.add(msg3);
        //5.发送消息
        SendResult result = producer.send(msgs);
        //发送状态
        SendStatus status = result.getSendStatus();
        System.out.println("发送结果:" + result);
        //线程睡1秒
        TimeUnit.SECONDS.sleep(1);
        //6.关闭生产者producer
        producer.shutdown();
    }
}
