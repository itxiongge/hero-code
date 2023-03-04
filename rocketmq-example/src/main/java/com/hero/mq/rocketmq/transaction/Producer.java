package com.hero.mq.rocketmq.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        //创建消息生产者producer，并制定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("group5");
        //指定Nameserver地址
        producer.setNamesrvAddr("192.168.200.128:9876;192.168.200.129:9876");
        //添加事务监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 执行本地事务
             * @param msg
             * @param arg
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                if (StringUtils.equals("TAGA", msg.getTags())) {
                    //模拟事务操作
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.equals("TAGB", msg.getTags())) {
                    //模拟事务操作
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (StringUtils.equals("TAGC", msg.getTags())) {
                    //模拟事务操作
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.UNKNOW;
            }
            /**
             * 该方法时MQ进行消息事务状态回查
             * @param msg
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("消息的Tag:" + msg.getTags());//TAGC
                return LocalTransactionState.COMMIT_MESSAGE;//默认回复已提交
            }
        });
        //启动producer
        producer.start();
        String[] tags = {"TAGA", "TAGB", "TAGC"};
        for (int i = 0; i < 3; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            Message msg = new Message("TransactionTopic", tags[i], ("Hello Hero" + i).getBytes());
            //5.发送消息
            SendResult result = producer.sendMessageInTransaction(msg, null);
            //发送状态
            SendStatus status = result.getSendStatus();
            System.out.println("发送结果:" + result);
            System.out.println("发送状态:" + status);
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }
        //6.不关闭生产者producer，会阻塞，等待MQ回查
        //producer.shutdown();
    }
}
