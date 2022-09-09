package com.hero.jvm.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*
-Xmx600m -Xms600m -XX:+PrintGCDetails
*/
public class HeapInstance {
    public static void main(String[] args) {
        //对象引用
        List<Picture> list = new ArrayList<>();
        while (true){//死循环
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Picture占用内存，4+12kb = 16KB
            //占用4KB
            //1M * 1000ms/20ms = 50M
            int length = new Random().nextInt(1024 * 1024);
            Picture e = new Picture(length);
            list.add(e);//4K + 12K =16K
        }
    }
}

class Picture{
    private byte[] pixels;
    public Picture(int length){
        this.pixels = new byte[length];//1024 * 1024 =1M
    }
}
