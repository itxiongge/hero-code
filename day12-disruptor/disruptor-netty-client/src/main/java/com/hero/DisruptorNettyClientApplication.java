package com.hero;

import com.hero.client.MessageConsumerImpl4Client;
import com.hero.disruptor.MessageConsumer;
import com.hero.disruptor.RingBufferWorkerPoolFactory;
import com.lmax.disruptor.YieldingWaitStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hero.client.NettyClient;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

@SpringBootApplication
public class DisruptorNettyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisruptorNettyClientApplication.class, args);

        MessageConsumer[] conusmers = new MessageConsumer[8];
        for (int i = 0; i < conusmers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Client("code:clientId:" + i);
            conusmers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024 * 1024,
                new YieldingWaitStrategy(),
                //new BlockingWaitStrategy(),
                conusmers);

        //建立连接 并发送消息
        new NettyClient().sendData();
    }
}
