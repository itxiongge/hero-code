package com.hero.jvm.object;

/**
 * 演示：
 * 1.对象可以在被GC时自我拯救。
 * 2.机会只有一次，对象的finalize()方法只会被系统自动调用一次
 */
public class finalizeEscapeGC {

    public static finalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("你瞅啥, 哥还活着 :)");
    }

    @Override//系统自动调用
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("执行 finalize() !");
        finalizeEscapeGC.SAVE_HOOK = this;//指定回去
    }

    public static void main(String[] args) throws Throwable {
        SAVE_HOOK = new finalizeEscapeGC();//强引用

        //对象第一次成功拯救自己
        SAVE_HOOK = null;//对象没有引用
        System.gc();//注意：GC不意味就是立即被干掉


        //因为finalize方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);//只要等就行
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();//你瞅啥, 哥还活着 :)
        } else {
            System.out.println("哦不, 哥死了 :(");
        }

        //下面这段代码与上面的完全相同，但是这次自救却失败了
        SAVE_HOOK = null;
        System.gc();//机会只有一次
        //因为finalize方法优先级很低，所以暂停0.5秒以等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();//
        } else {
            System.out.println("哦不, 哥死了 :(");//哦不, 哥死了 :(
        }
    }
}
