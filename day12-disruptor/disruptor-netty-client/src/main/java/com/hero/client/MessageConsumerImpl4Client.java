package com.hero.client;

import com.hero.disruptor.MessageConsumer;
import com.hero.entity.TranslatorData;
import com.hero.entity.TranslatorDataWapper;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class MessageConsumerImpl4Client extends MessageConsumer {

    public MessageConsumerImpl4Client(String consumerId) {
        super(consumerId);
    }

    public void onEvent(TranslatorDataWapper event) throws Exception {
        TranslatorData response = event.getData();
        ChannelHandlerContext ctx = event.getCtx();
        //业务逻辑处理:
        try {
            System.err.println("Client端: id= " + response.getId()
            + ", name= " + response.getName()
            + ", message= " + response.getMessage());
        } finally {
            ReferenceCountUtil.release(response);
        }
    }

}
