package com.hero.nio.chat;

import java.util.Scanner;

//启动聊天程序客户端
public class TestChat {
    public static void main(String[] args) throws Exception {
        ChatClient chatClient=new ChatClient();

        new Thread(() -> {
            //监听服务器消息
            while(true){
                try {
                    Thread.sleep(2000);
                    chatClient.receiveMsg();
                }catch (Exception e){
                    //e.printStackTrace();
                    System.out.println("请检查服务器是否关闭~");
                }

            }
        }).start();

        Scanner scanner=new Scanner(System.in);
        while (scanner.hasNextLine()){
            String msg=scanner.nextLine();
            chatClient.sendMsg(msg);
        }
    }
}
