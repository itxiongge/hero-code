package com.hero.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class RocketmqTranMessageTest {
    public static final String ROCKETMQ_SERVER = "192.168.56.204:9876;192.168.56.204:9877";
    private static final String TOPIC_TRAN= "Topic_Tran";
    private static final String TAG_A = "tag_Tran";
    private static final String GROUP = "Tran_TEST_GROUP";

    //Producer端发送同步消息
    @Test
    public void produceTran() throws Exception {
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer(GROUP);
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        producer.setExecutorService(executorService);

        //设置事务消息监听
        producer.setTransactionListener(transactionListener);
        // 设置NameServer的地址
        producer.setNamesrvAddr(ROCKETMQ_SERVER);
        producer.start();
        String[] tags = new String[] {"TagA", "TagB", "TagC"};
        for (int i = 0; i < 3; i++) {
            try {
                Message msg = new Message(TOPIC_TRAN, tags[i % tags.length], "id_" + i,
                                ("Hero " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, null);


                System.out.printf("%s%n", sendResult);
                Thread.sleep(10);
            } catch (MQClientException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
    }


    //消费者和普通消费者一样
    @Test
    public void consumer() throws Exception {


        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GROUP);

        // 设置NameServer的地址
        consumer.setNamesrvAddr(ROCKETMQ_SERVER);

        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe(TOPIC_TRAN, "*");
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


    static class TransactionListenerImpl implements TransactionListener {

        //执行业务，保存本地事务，下订单的操作
        @Override
        public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            log.info("执行本地事务：{}, {}", msg.getTransactionId(), msg.getTags());

            if (StringUtils.equals("TagA", msg.getTags())) {
                return LocalTransactionState.COMMIT_MESSAGE;
            } else if (StringUtils.equals("TagB", msg.getTags())) {
                return LocalTransactionState.ROLLBACK_MESSAGE;
            } else if (StringUtils.equals("TagC", msg.getTags())) {
                return LocalTransactionState.UNKNOW;
            }

            return null;
        }

        //这里查询本地事务状态 ，比如本地订单是否操作成功
        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
            log.info("检查 LocalTransaction ：{}, {}", msg.getTransactionId(), msg.getTags());
            return LocalTransactionState.COMMIT_MESSAGE;
        }
    }
}
