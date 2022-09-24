package com.hero.netty.demo02chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//自定义一个服务器端业务处理类
public class NettyChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static List<Channel> channels = new CopyOnWriteArrayList<>();

    @Override  //通道就绪
    public void channelActive(ChannelHandlerContext ctx)  {
        Channel inChannel=ctx.channel();
        channels.add(inChannel);
        System.out.println("[Server]:"+inChannel.remoteAddress().toString().substring(1)+"上线");
    }
    @Override  //通道未就绪
    public void channelInactive(ChannelHandlerContext ctx)  {
        Channel inChannel=ctx.channel();
        channels.remove(inChannel);
        System.out.println("[Server]:"+inChannel.remoteAddress().toString().substring(1)+"离线");
    }
    @Override  //读取数据
    protected void channelRead0(ChannelHandlerContext ctx, String s)  {
        Channel inChannel=ctx.channel();
        System.out.println("s = " + s);//为什么可以拿到字符串呢？字节数组呢！已经进行过解码
        //遍历所有上线Channel，广播消息
        for(Channel channel:channels){
            if(channel!=inChannel){
                //写出的操作，编码，字符串-->字节数组
                channel.writeAndFlush("["+inChannel.remoteAddress().toString().substring(1)+"]"+"说："+s+"\n");
            }
        }
    }

}
