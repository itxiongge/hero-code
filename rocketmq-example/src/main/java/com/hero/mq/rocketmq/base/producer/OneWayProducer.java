package com.hero.mq.rocketmq.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送单向消息
 */
public class OneWayProducer {
    public static void main(String[] args) throws Exception, MQBrokerException {
        //1.创建消息生产者producer，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //指定Nameserver地址
        producer.setNamesrvAddr("192.168.200.128:9876;192.168.200.129:9876");
        //启动producer
        producer.start();
        for (int i = 0; i < 10; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            Message msg = new Message("base", "Tag3", ("Hello Hero，单向消息" + i).getBytes());
            //5.发送单向消息，没有任何返回结果
            producer.sendOneway(msg);
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }
        //6.如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
