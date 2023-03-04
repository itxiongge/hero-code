package com.hero.demo;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class RocketmqBasiMessageTest {

    public static final String ROCKETMQ_SERVER = "192.168.56.204:9876;192.168.56.204:9877";

    @Test
    public void consumerTest() throws Exception {
        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("cgroup1");

        // 设置NameServer的地址
        consumer.setNamesrvAddr(ROCKETMQ_SERVER);
        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe("TopicTest", "*");

        consumer.setMaxReconsumeTimes(1); //自定义重试次数

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
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.printf("Consumer Started.%n");
        LockSupport.parkNanos(Integer.MAX_VALUE * 1000L * 1000L);
    }

    //Producer端发送同步消息
    @Test
    public void produceSynTest() throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("pgroup1");
        // 设置NameServer的地址
        producer.setNamesrvAddr(ROCKETMQ_SERVER);
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 4; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hero" + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg, 50000);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

    public static void main(String[] args) {
        log.info(UUID.randomUUID().toString().replaceAll("-",""));
    }

    @Test
    public void produceASynTest() throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 设置NameServer的地址
        producer.setNamesrvAddr(ROCKETMQ_SERVER);
        // 启动Producer实例
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 100;
        // 根据消息数量实例化倒计时计算器
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0; i < messageCount; i++) {
            final int index = i;
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest",
                    "TagA",
                    "OrderID188",
                    ("Hero" + index).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // SendCallback接收异步返回结果的回调
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%s 线程 %-10d OK %s %n", Thread.currentThread().getName() ,index, sendResult);
                    countDownLatch.countDown();
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        // 等待5s
        countDownLatch.await(5, TimeUnit.SECONDS);
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();

    }

    @Test
    public void onewayProducer() throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 设置NameServer的地址
        producer.setNamesrvAddr(ROCKETMQ_SERVER);
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 100; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hero" + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送单向消息，没有任何返回结果
            producer.sendOneway(msg);

        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }



    @Test
    public void batchProducer() throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 设置NameServer的地址
        producer.setNamesrvAddr(ROCKETMQ_SERVER);
        // 启动Producer实例
        producer.start();

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("TopicTest", "TagA", "OrderID001", "Hero 0".getBytes()));
        messages.add(new Message("TopicTest", "TagA", "OrderID002", "Hero 1".getBytes()));
        messages.add(new Message("TopicTest", "TagA", "OrderID003", "Hero 2".getBytes()));
        try {
            producer.send(messages);
        } catch (Exception e) {
            e.printStackTrace();
            //处理error
        }

        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

    @Test
    public void batchProducerWithListSplitter() throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 设置NameServer的地址
        producer.setNamesrvAddr(ROCKETMQ_SERVER);
        // 启动Producer实例
        producer.start();

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("TopicTest", "TagA", "OrderID001", "Hero 0".getBytes()));
        messages.add(new Message("TopicTest", "TagA", "OrderID002", "Hero 1".getBytes()));
        messages.add(new Message("TopicTest", "TagA", "OrderID003", "Hero 2".getBytes()));

        //把大的消息分裂成若干个小的消息
        ListSplitter splitter = new ListSplitter(messages);
        while (splitter.hasNext()) {
            try {
                List<Message> sublist = splitter.next();
                producer.send(sublist);
            } catch (Exception e) {
                e.printStackTrace();
                //处理error
            }
        }


        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }


    public class ListSplitter implements Iterator<List<Message>> {
        private final int SIZE_LIMIT = 1024 * 1024 * 4;
        private final List<Message> messages;
        private int currIndex;

        public ListSplitter(List<Message> messages) {
            this.messages = messages;
        }

        @Override
        public boolean hasNext() {
            return currIndex < messages.size();
        }

        @Override
        public List<Message> next() {
            int nextIndex = currIndex;
            int totalSize = 0;
            for (; nextIndex < messages.size(); nextIndex++) {
                Message message = messages.get(nextIndex);

                //topic +body

                int tmpSize = message.getTopic().length() + message.getBody().length;

                // peperties

                Map<String, String> properties = message.getProperties();
                for (Map.Entry<String, String> entry : properties.entrySet()) {
                    tmpSize += entry.getKey().length() + entry.getValue().length();
                }

                // 增加日志的开销20字节
                tmpSize = tmpSize + 20;
                if (tmpSize > SIZE_LIMIT) {
                    //单个消息超过了最大的限制
                    //忽略,否则会阻塞分裂的进程
                    if (nextIndex - currIndex == 0) {
                        //假如下一个子列表没有元素,则添加这个子列表然后退出循环,否则只是退出循环
                        nextIndex++;
                    }
                    break;
                }
                if (tmpSize + totalSize > SIZE_LIMIT) {
                    break;
                } else {
                    totalSize += tmpSize;
                }

            }
            List<Message> subList = messages.subList(currIndex, nextIndex);
            currIndex = nextIndex;
            return subList;
        }
    }


}
