package com.hero.client;

import com.hero.disruptor.MessageProducer;
import com.hero.disruptor.RingBufferWorkerPoolFactory;
import com.hero.entity.TranslatorData;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        try {
//            TranslatorData response = (TranslatorData)msg;
//            System.err.println("Client端: id= " + response.getId()
//                    + ", name= " + response.getName()
//                    + ", message= " + response.getMessage());
//        } finally {
//            //一定要注意 用完了缓存 要进行释放
//            ReferenceCountUtil.release(msg);
//        }

        TranslatorData response = (TranslatorData)msg;
        String producerId = "code:sessionId:002";
        MessageProducer messageProducer = RingBufferWorkerPoolFactory.getInstance().getMessageProducer(producerId);
        messageProducer.onData(response, ctx);
    }
}
