package com.hero.demo.service;

import com.hero.demo.dto.UserDTO;
import com.hero.demo.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ProducerService {

	public static final String MQ_TOPIC = "SPRING-BOOT-DEMO";

	// 直接注入使用，用于发送消息到broker服务器
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

	/**
     * 普通发送（这里的参数对象User可以随意定义，可以发送个对象，也可以是字符串等）
     */
    public void sendSomeThing() {
        UserDTO user = UserDTO.builder().userId(10001L).username("Hero1").build();
        send(user);
        System.out.println("send finished!");
    }
	/**
     * 普通发送
     */
    public void send(UserDTO user) {
        Message message = MessageBuilder.withPayload(user)
                .setHeader("KEYS", user.getUserId()).build();
        SendResult result = rocketMQTemplate.syncSend(MQ_TOPIC + ":tag1", message);
//        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, Hero!");
        rocketMQTemplate.send(MQ_TOPIC + ":tag1", MessageBuilder.withPayload(user).build()); // 等价于上面一行
    }

    /**
     * 发送同步消息（阻塞当前线程，等待broker响应发送结果，这样不太容易丢失消息）
     * （msgBody也可以是对象，sendResult为返回的发送结果）
     */
    public SendResult sendMsg(String msgBody) {
        SendResult sendResult = rocketMQTemplate.syncSend(MQ_TOPIC, MessageBuilder.withPayload(msgBody).build());
        log.info("【sendMsg】sendResult={}", JsonUtil.pojoToJson(sendResult));
        return sendResult;
    }

	/**
     * 发送异步消息（通过线程池执行发送到broker的消息任务，执行完后回调：在SendCallback中可处理相关成功失败时的逻辑）
     * （适合对响应时间敏感的业务场景）
     */
    public void sendAsyncMsg(String msgBody) {
        rocketMQTemplate.asyncSend(MQ_TOPIC, MessageBuilder.withPayload(msgBody).build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                // 处理消息发送成功逻辑
            }
            @Override
            public void onException(Throwable throwable) {
                // 处理消息发送异常逻辑
            }
        });
    }
    @Value("${rocketmq.producer.send-message-timeout}")
    private Integer messageTimeOut;
	/**
     * 发送延时消息（上面的发送同步消息，delayLevel的值就为0，因为不延时）
     * 在start版本中 延时消息一共分为18个等级分别为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    public void sendDelayMsg(String msgBody, int delayLevel) {
        rocketMQTemplate.syncSend(MQ_TOPIC, MessageBuilder.withPayload(msgBody).build(), messageTimeOut, delayLevel);
    }

    /**
     * 发送单向消息（只负责发送消息，不等待应答，不关心发送结果，如日志）
     */
    public void sendOneWayMsg(String msgBody) {
        rocketMQTemplate.sendOneWay(MQ_TOPIC, MessageBuilder.withPayload(msgBody).build());
    }

	/**
     * 发送带tag的消息，直接在topic后面加上":tag"
     */
    public SendResult sendTagMsg(String msgBody) {
        return rocketMQTemplate.syncSend(MQ_TOPIC + ":tag2", MessageBuilder.withPayload(msgBody).build());
    }
}
