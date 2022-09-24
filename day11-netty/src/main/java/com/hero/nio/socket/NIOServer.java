package com.hero.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

//网络服务器端程序
public class NIOServer {
    public static void main(String[] args) throws Exception {
        //1. 开启一个ServerSocketChannel通道（对象）
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2. 开启一个Selector选择器
        Selector selector = Selector.open();
        //3. 绑定端口号9999
        System.out.println("服务端 启动....");
        System.out.println("初始化端口 9999 ");
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //4. 配置非阻塞方式
        serverSocketChannel.configureBlocking(false);
        //5. Selector选择器注册ServerSocketChannel通道，绑定连接操作
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //6. 循环执行：监听连接事件及读取数据操作
        while (true) {
            //6.1 监控客户端连接：
            // selecto.select()方法返回的是客户端的通道数，如果为0，则说明没有客户端连接。
            //nio非阻塞式的优势
            if (selector.select(2000) == 0) {//非阻塞
                System.out.println("Server：门庆没有找我，去找王妈妈搞点兼职做~");
                continue;
            }
            //6.2 得到SelectionKey,判断通道里的事件
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            //遍历所有SelectionKey
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                //客户端先连接上，处理连接事件，然后客户端会向服务端发信息，再处理读取客户端数据事件。
                if (key.isAcceptable()) {//客户端连接请求事件
                    System.out.println("OP_ACCEPT");
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //注册通道 ,将通道交给selector选择器进行监控。
                    //参数01-选择器
                    //参数02-服务器要监控读事件，客户端发send数据，服务端读read数据
                    //参数03-客户端传过来的数据要放在缓冲区
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {//读取客户端数据事件
                    //数据在通道中，先得到通道
                    SocketChannel channel = (SocketChannel) key.channel();
                    //取到一个缓冲区，nio读写数据都是基于缓冲区。
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    //从通道中将客户端发来的数据读到缓冲区
                    channel.read(buffer);
                    System.out.println("客户端发来数据：" + new String(buffer.array()));
                }
                // 6.3 手动从集合中移除当前key,防止重复处理
                keyIterator.remove();
            }
        }
    }
}

