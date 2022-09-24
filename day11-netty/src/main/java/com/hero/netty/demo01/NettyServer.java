package com.hero.netty.demo01;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws Exception{
        //1. 创建一个线程组：接收客户端连接
        EventLoopGroup bossGroup =new NioEventLoopGroup();
        //2. 创建一个线程组：处理网络操作
        EventLoopGroup workerGroup =new NioEventLoopGroup();
        //3. 创建服务器端启动助手来配置参数
        ServerBootstrap serverBootstrap=new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup) //4.设置两个线程组
                .channel(NioServerSocketChannel.class) //5.使用NioServerSocketChannel作为服务器端通道的实现
                .option(ChannelOption.SO_BACKLOG, 128) //6.设置线程队列中等待连接的个数
                .childOption(ChannelOption.SO_KEEPALIVE, true) //7.保持长连接
                .childHandler(new ChannelInitializer<SocketChannel>() {  //8. 创建一个通道初始化对象
                    public void initChannel(SocketChannel sc) {   //9. 往Pipeline链中添加自定义的handler类
                        sc.pipeline().addLast(new NettyServerHandler());
                    }
                });
        System.out.println("......服务端 启动中 init port:9999 ......");
        ChannelFuture cf=serverBootstrap.bind(9999).sync();  //10. 绑定端口 bind方法是异步的  sync方法是同步阻塞的
        System.out.println("......服务端 启动成功 ......");

        //11. 关闭通道，关闭线程组
        cf.channel().closeFuture().sync(); //异步
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
