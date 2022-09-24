package com.hero.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

//聊天程序客户端
public class ChatClient {
    private final String HOST = "121.199.163.228"; //服务器地址
    private int PORT = 9999; //服务器端口
    private SocketChannel socketChannel; //网络通道
    private String userName; //聊天用户名

    public ChatClient() throws IOException {
        //1. 得到一个网络通道
        socketChannel=SocketChannel.open();
        //2. 设置非阻塞方式
        socketChannel.configureBlocking(false);
        //3. 提供服务器端的IP地址和端口号
        InetSocketAddress address=new InetSocketAddress(HOST,PORT);
        //4. 连接服务器端
        if(!socketChannel.connect(address)){
            while(!socketChannel.finishConnect()){  //nio作为非阻塞式的优势
                System.out.println("Client:连接服务器端的同时，咱也别闲着，去搞点兼职~");
            }
        }
        //5. 得到客户端IP地址和端口信息，作为聊天用户名使用
        userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println("---------------Client(" + userName + ") is ready---------------");
    }


    //向服务器端发送数据
    public void sendMsg(String msg) throws Exception{
        if(msg.equalsIgnoreCase("bye")){
            socketChannel.close();
            return;
        }
        msg = userName + "说："+ msg;
        ByteBuffer buffer=ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(buffer);
    }


    //从服务器端接收数据
    public void receiveMsg() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        if (!socketChannel.isOpen()) {
            return;
        }
        int size=socketChannel.read(buffer);
        if(size>0){
            String msg=new String(buffer.array());
            System.out.println(msg.trim());
        }

    }
}
