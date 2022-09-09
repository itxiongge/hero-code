package com.hero.jvm.object;

import org.openjdk.jol.info.ClassLayout;

public class ObjLock02{
    public static void main(String[] args) {
        Hero a = new Hero();
        //赋值之前，这个对象多大：
        System.out.println("new A:" + ClassLayout.parseInstance(a).toPrintable());
        a.setFlag(true);//1bytes
        a.setI(1);//4
        a.setStr("ABC");//4
        //赋值之后，这个对象多大：
        System.out.println("赋值 A:" + ClassLayout.parseInstance(a).toPrintable());
    }

    static class Hero {
        private boolean flag;
        private int i;
        private String str;

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public void setI(int i) {
            this.i = i;
        }
    }
}
