package com.hero.distributedlock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//基于Zookeeper的分布式锁的实现
@Slf4j
public class DLHeroLock implements Lock {

    //DLHeroLock的节点链接
    private static final String ZK_PATH = "/DLHero/lock";
    private static final String LOCK_PREFIX = ZK_PATH + "/";//锁前缀

    private static final long WAIT_TIME = 1000;//锁排队超时时间


    private String locked_path = null;//当前节点，举个栗子：/DLHero/lock/0000000015
    private String prior_path = null;//当前节点的上一个节点，举个栗子：/DLHero/lock/0000000014
    private String locked_short_path = null;//节点短路径，举个栗子：0000000015

    final AtomicInteger lockCount = new AtomicInteger(0);//重入锁计数器
    private Thread thread;//本地线程

    private CuratorFramework client = null;//zk客户端


    public DLHeroLock() {
        ZKClient.instance.init();
        if (!ZKClient.instance.isNodeExist(ZK_PATH)) {
            ZKClient.instance.createNode(ZK_PATH, null);
        }
        client = ZKClient.instance.getClient();
    }

    @Override
    public boolean lock() {//某个线程进入来加锁
        //可重入，确保同一线程，可以重复加锁
        synchronized (this) {
            if (lockCount.get() == 0) {//第一次获取锁
                thread = Thread.currentThread();
                lockCount.incrementAndGet();//线程安全的操作
            } else {//第二次获取锁
                if (!thread.equals(Thread.currentThread())) {
                    return false;
                }
                lockCount.incrementAndGet();
                return true;
            }
        }
        try {
            boolean locked = false;
            //第一步：首先尝试着去加锁【情况01】
            locked = tryLock();
            if (locked) {
                return true;
            }
            //第二步：如果加锁失败就去等待，情况02【飞一会】
            //我已经知道自己在获取锁队列中的什么位置，现在锁竞争还是很激烈的
            //我也知道前面在排队的节点是谁
            //一个节点对应一个线程
            while (!locked) {
                await();
                //获取等待的子节点列表
                List<String> waiters = getWaiters();
                //判断，是否加锁成功
                if (checkLocked(waiters)) {
                    locked = true;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            unlock();//释放锁并减少计数
        }
        return false;
    }

    /**
     * 释放锁
     * @return 是否成功释放锁
     */
    @Override
    public boolean unlock() {
        //只有加锁的线程，能够解锁
        if (!thread.equals(Thread.currentThread())) {
            return false;
        }
        //减少可重入的计数
        int newLockCount = lockCount.decrementAndGet();
        //计数不能小于0
        if (newLockCount < 0) {
            throw new IllegalMonitorStateException("重入锁计数不可为负数: " + locked_path);
        }
        //如果计数不为0，直接返回，相当于不需要释放掉锁，加锁的次数还没有消减掉
        if (newLockCount != 0) {
            return true;
        }
        //在解锁的时候，只有newLockCount = 0才会真正意义进行锁的是否，后续的线程才可以操作共享变量
        //删除临时节点
        try {
            if (ZKClient.instance.isNodeExist(locked_path)) {
                client.delete().forPath(locked_path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //线程排队实现
    private void await() throws Exception {
        if (null == prior_path) {
            throw new Exception("prior_path error");
        }

        final CountDownLatch latch = new CountDownLatch(1);//倒数门闩

        //为当前节点的上一个节点，加一个监听事件，上一个节点删除之后才会回调当前的方法

        //订阅比自己次小顺序节点的删除事件
        Watcher w = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("监听到的变化 watchedEvent = " + watchedEvent);
                log.info("[WatchedEvent]节点删除");
                latch.countDown();
            }
        };

        client.getData().usingWatcher(w).forPath(prior_path);
        /*
                //订阅比自己次小顺序节点的删除事件
                TreeCache treeCache = new TreeCache(client, prior_path);
                TreeCacheListener l = new TreeCacheListener() {
                    @Override
                    public void childEvent(CuratorFramework client,
                                           TreeCacheEvent event) throws Exception {
                        ChildData data = event.getData();
                        if (data != null) {
                            switch (event.getType()) {
                                case NODE_REMOVED:
                                    log.debug("[TreeCache]节点删除, path={}, data={}",
                                            data.getPath(), data.getData());

                                    latch.countDown();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                };

                treeCache.getListenable().addListener(l);
                treeCache.start();*/
        //超时时间WAIT_TIME
        latch.await(WAIT_TIME, TimeUnit.SECONDS);
    }
    /**
     * 尝试加锁
     * @return 是否加锁成功
     * @throws Exception 异常
     */
    private boolean tryLock() throws Exception {
        //创建临时Znode
        locked_path = ZKClient.instance.createEphemeralSeqNode(LOCK_PREFIX);
        //  /DLHero/lock/0000000015
        //然后获取所有节点
        List<String> waiters = getWaiters();
        if (null == locked_path) {
            throw new Exception("zk error");
        }
        //  0000000015
        //取得加锁的排队编号
        locked_short_path = getShorPath(locked_path);

        //获取等待的子节点列表，判断自己是否第一个
        if (checkLocked(waiters)) {
            return true;
        }
        // 判断自己排第几个
        int index = Collections.binarySearch(waiters, locked_short_path);
        if (index < 0) { // 网络抖动，获取到的子节点列表里可能已经没有自己了
            throw new Exception("节点没有找到: " + locked_short_path);
        }
        //如果自己没有获得锁，则要监听前一个节点
        prior_path = ZK_PATH + "/" + waiters.get(index - 1);

        return false;
    }
    //截取加锁编号
    private String getShorPath(String locked_path) {
        int index = locked_path.lastIndexOf(ZK_PATH + "/");
        if (index >= 0) {
            index += ZK_PATH.length() + 1;
            return index <= locked_path.length() ? locked_path.substring(index) : "";
        }
        return null;
    }
    //检查是否持有锁
    private boolean checkLocked(List<String> waiters) {
        //节点按照编号，升序排列
        Collections.sort(waiters);
        // 如果是第一个，代表自己已经获得了锁
        if (locked_short_path.equals(waiters.get(0))) {
            log.info("成功的获取分布式锁,节点为{}", locked_short_path);
            return true;
        }
        return false;
    }

    /**
     * 从zookeeper中拿到所有等待节点
     */
    protected List<String> getWaiters() {
        List<String> children = null;
        try {
            children = client.getChildren().forPath(ZK_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return children;
    }
}
