package com.hero.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class RocketmqTaggedMessageTest {

    public static final String ROCKETMQ_SERVER = "192.168.56.204:9876;192.168.56.204:9877";
    private static final String TOPIC_TEST = "TopicTest";
    private static final String TAG_A = "tag_a";
    private static final String GROUP = "Tagged_TEST_GROUP";

    //Producer端发送同步消息
    @Test
    public void produceTagged() throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(GROUP);
        // 设置NameServer的地址
        producer.setNamesrvAddr(ROCKETMQ_SERVER);
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 100; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message(TOPIC_TEST /* Topic */,
                    TAG_A /* Tag */,
                    ("Hero" + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

    @Test
    public void consumerTAG_A() throws Exception {


        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP);

        // 设置NameServer的地址
        consumer.setNamesrvAddr(ROCKETMQ_SERVER);

        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(TOPIC_TEST, TAG_A);
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);

                for (int i = 0; i < msgs.size(); i++) {
                    MessageExt msg = msgs.get(i);
                    String content = new String(msg.getBody());
                    log.info("收到消息：{}, {}", i, msg.getMsgId() + " " + msg.getTopic() + " " + msg.getTags() + " " + content);
                    try {
                        //消费者的业务代码
//                        redisSeckillServiceImpl.executeSeckill(dto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer Started.%n");
        LockSupport.parkNanos(Integer.MAX_VALUE * 1000L * 1000L);
    }

    @Test
    public void produceTaggedAttributes() throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer(GROUP);
        // 设置NameServer的地址
        producer.setNamesrvAddr(ROCKETMQ_SERVER);
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 100; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message(TOPIC_TEST /* Topic */,
                    TAG_A /* Tag */,
                    ("Hero" + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            msg.putUserProperty("a", String.valueOf(i));
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }


    @Test
    public void consumerTaggedAttributes() throws Exception {


        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP);

        // 设置NameServer的地址
        consumer.setNamesrvAddr(ROCKETMQ_SERVER);

        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(TOPIC_TEST, MessageSelector.bySql("a between 0 and 3"));
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);

                for (int i = 0; i < msgs.size(); i++) {
                    MessageExt msg = msgs.get(i);
                    String content = new String(msg.getBody());
                    log.info("收到消息：{}, {}", i, msg.getMsgId() + " " + msg.getTopic() + " " + msg.getTags() + " " + content);
                    try {
                        //消费者的业务代码
//                        redisSeckillServiceImpl.executeSeckill(dto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer Started.%n");
        LockSupport.parkNanos(Integer.MAX_VALUE * 1000L * 1000L);

    }
}
