package com.hero.netty.demo03EncoderDecoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyEncoderDecoderServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BookMessage.Book book=(BookMessage.Book)msg;
        System.out.println("客户端 msg："+book.getName());
    }

}
