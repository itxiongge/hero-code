package com.hero.netty.demo02chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

//聊天程序服务器端
public class NettyChatServer {

    private int port; //服务器端端口号

    public NettyChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        //定义线程组bossGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //定义线程组workerGroup
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            //往pipeline链中添加一个解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //往pipeline链中添加一个编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            //往pipeline链中添加自定义的handler(业务处理类)
                            pipeline.addLast(new NettyChatServerHandler());
                        }
                    });
            System.out.println("网络真人聊天室 Server 启动......");
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("网络真人聊天室 Server 关闭......");
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyChatServer(9999).run();
    }
}
