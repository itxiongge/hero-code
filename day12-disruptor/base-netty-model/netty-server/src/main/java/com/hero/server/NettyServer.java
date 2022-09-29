package com.hero.server;

import com.hero.codec.MarshallingCodeCFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServer {

    public NettyServer() {
        //1. 创建两个工作线程组: 一个用于接受网络请求的线程组，另一个用于实际处理业务请求的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //2 辅助类构建netty模型
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {

            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class) //netty基于nio
                    .option(ChannelOption.SO_BACKLOG, 1024) //netty基于长连接，1024个就够用
                    // 接受数据要先放在缓冲中。表示缓存区动态调配（自适应，避免缓冲区大了或小了）
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    // 将缓存区池化，提高性能。
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO)) //日志
                    // 处理接受的数据做回调处理，在这里不要去处理业务，会影响netty性能。
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            //数据需要序列化（java对象要转成二进制数据才能在网络上传输）
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            sc.pipeline().addLast(new ServerHandler());  //处理业务
                        }
                    });
            //绑定端口，同步等等请求连接
            ChannelFuture cf = serverBootstrap.bind(8765).sync();
            System.err.println("Server Startup...");
            cf.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅停机(数据发送完再停机)
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            System.err.println("Sever ShutDown...");
        }
    }

}
