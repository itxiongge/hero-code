package com.hero.mq.rocketmq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        //创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //指定Nameserver地址
        producer.setNamesrvAddr("192.168.200.128:9876;192.168.200.129:9876");
        //启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("base", "Tag1", ("Hello Hero" + i).getBytes());
            //5.发送消息
            SendResult result = producer.send(msg);
            //获取状态
            SendStatus status = result.getSendStatus();
            System.out.println("发送结果:" + result);
            System.out.println("发送状态:" + status);
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }
        //6.关闭生产者producer
        producer.shutdown();
    }
}
