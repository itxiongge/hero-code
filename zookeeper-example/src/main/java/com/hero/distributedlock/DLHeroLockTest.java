package com.hero.distributedlock;

import com.hero.cocurrent.FutureTaskScheduler;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;

@Slf4j
public class DLHeroLockTest {

    int count = 0;//共享变量

    @Test
    public void testLock() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            FutureTaskScheduler.add(() -> {
                //创建锁
                DLHeroLock lock = new DLHeroLock();
                lock.lock();//加锁

                //每条线程，执行共享变量的10次累加
                for (int j = 0; j < 10000; j++) {
                    count++;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("count ========== " + count);
                //释放锁
                lock.unlock();
            });
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testInterProcessMutex() throws InterruptedException {

        CuratorFramework client = ZKClient.instance.getClient();

        final InterProcessMutex zkMutex = new InterProcessMutex(client, "/mutex");//分布式的

        for (int i = 0; i < 10; i++) {
            FutureTaskScheduler.add(() -> {
                try {
                    //获取互斥锁
                    zkMutex.acquire();

                    for (int j = 0; j < 10000; j++) {
                        count++;//共享变量累加
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("count ========== " + count);
                    zkMutex.release();//释放互斥锁

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
        Thread.sleep(Integer.MAX_VALUE);
    }


}
