package com.hero.demo.service;

import com.hero.demo.dto.UserDTO;
import com.hero.demo.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Slf4j
@Component
public class MQConsumerListener {

    public static final String MQ_TOPIC = "SPRING-BOOT-DEMO";

    // topic需要和生产者的topic一致，consumerGroup属性是必须指定
    @Service
    @RocketMQMessageListener(topic = MQ_TOPIC, selectorExpression = "*", consumerGroup = "Con_Group_One")
    public class Consumer1 implements RocketMQListener<UserDTO> {
        // 监听到消息就会执行此方法
        @Override
        public void onMessage(UserDTO user) {
            log.info("监听到消息：user={}", JsonUtil.pojoToJson(user));
        }
    }

    // 注意：Consumer1和Consumer2最好是添加tag做区分,不然生产者发送一条消息，这两个都会去消费
    // 如果类型不同会有报错，所以实际运用中最好加上tag
    @Service
    @RocketMQMessageListener(topic = MQ_TOPIC, consumerGroup = "Con_Group_Two")
    public class Consumer2 implements RocketMQListener<String> {
        @Override
        public void onMessage(String str) {
            log.info("监听到消息：str={}", str);
        }
    }

	// MessageExt：扩展消息类型，
    // 不管发送的是String还是对象，都可接收，当然也可以像上面明确指定类型，当然，指定类型较方便，制定类型，控制比较细粒度
    @Service
    @RocketMQMessageListener(topic = MQ_TOPIC, selectorExpression = "tag2", consumerGroup = "Con_Group_Three")
    public class Consumer3 implements RocketMQListener<MessageExt> {
        @Override
        public void onMessage(MessageExt messageExt) {
            byte[] body = messageExt.getBody();
            String msg = new String(body);
            log.info("监听到消息：msg={}", msg);
        }
    }
}
