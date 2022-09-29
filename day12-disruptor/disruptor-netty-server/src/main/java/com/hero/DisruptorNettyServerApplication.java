package com.hero;

import com.hero.disruptor.MessageConsumer;
import com.hero.disruptor.RingBufferWorkerPoolFactory;
import com.hero.server.MessageConsumerImpl4Server;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hero.server.NettyServer;

@SpringBootApplication
public class DisruptorNettyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisruptorNettyServerApplication.class, args);

        MessageConsumer[] conusmers = new MessageConsumer[8];
        for(int i =0; i < conusmers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Server("code:serverId:" + i);
            conusmers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024*1024,
                new YieldingWaitStrategy(),
                //new BlockingWaitStrategy(),
                conusmers);

        new NettyServer();
    }
}
