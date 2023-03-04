package com.hero.mq.rocketmq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        //创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //指定Nameserver地址
        producer.setNamesrvAddr("192.168.200.128:9876;192.168.200.129:9876");
        //启动producer
        producer.start();
        for (int i = 0; i < 10; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            Message msg = new Message("base", "Tag2", ("Hello Hero" + i).getBytes());
            //5.发送异步消息
            producer.send(msg, new SendCallback() {
                //发送成功回调函数
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送结果：" + sendResult);
                }
                //发送失败回调函数
                public void onException(Throwable e) {
                    System.out.println("发送异常：" + e);
                }
            });
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }
        //6.关闭生产者producer
        producer.shutdown();
    }
}
