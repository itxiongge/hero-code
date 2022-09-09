package com.hero.jvm.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class TestReference {
    public static void main(String[] args) {
        //强引用
        //软引用
//        Object obj = new Object();
//        SoftReference<Object> sf = new SoftReference<Object>(obj);
//        obj = null;
//        Object o = sf.get();//有时候会返回null
//        System.out.println("o = " + o);
        //弱引用
//        Object obj = new Object();
//        WeakReference<Object> wf = new WeakReference<Object>(obj);
//        obj = null;
//        System.gc();
//        Object o = wf.get();//有时候会返回null
//        boolean enqueued = wf.isEnqueued();//返回是否被垃圾回收器标记为即将回收的垃圾
//        System.out.println("o = " + o);
//        System.out.println("enqueued = " + enqueued);

        //虚引用
        Object obj = new Object();
        PhantomReference<Object> pf = new PhantomReference<Object>(obj, new ReferenceQueue<>());
        obj=null;
        Object o = pf.get();//永远返回null
        boolean enqueued = pf.isEnqueued();//返回是否从内存中已经删除
        System.out.println("o = " + o);
        System.out.println("enqueued = " + enqueued);
    }
}
