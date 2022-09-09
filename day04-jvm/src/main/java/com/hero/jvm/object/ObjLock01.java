package com.hero.jvm.object;

import org.openjdk.jol.info.ClassLayout;

public class ObjLock01 {
    public static void main(String[] args) {
        //占用内存空间是多大呢？
        //12bytes?
        //16bytes? -->
        Object o = new Object();
        System.out.println("new Object:" + ClassLayout.parseInstance(o).toPrintable());
    }
}
