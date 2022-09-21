package com.hero.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//BIO 服务器端程序
public class TCPServer {
    public static void main(String[] args) throws Exception {
        //1.创建ServerSocket对象
        System.out.println("服务端 启动....");
        System.out.println("初始化端口 9999 ");
        ServerSocket ss = new ServerSocket(9999); //端口号

        while (true) {
            //2.监听客户端
            Socket s = ss.accept(); //阻塞
            //3.从连接中取出输入流来接收消息
            InputStream is = s.getInputStream(); //阻塞
            byte[] b = new byte[10];
            is.read(b);
            String clientIP = s.getInetAddress().getHostAddress();
            System.out.println(clientIP + "说:" + new String(b).trim());
            //4.从连接中取出输出流并回话
            OutputStream os = s.getOutputStream();
            os.write("没钱".getBytes());
            //5.关闭
            s.close();
        }
    }
}
