package com.hero.lock;

import com.hero.DataApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataApplication.class)
public class RedissonLockDemo {
    @Autowired
    private RedissonClient redissonClient;
    private static int stock = 100;

    @Test
    public void testLock() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                RLock lock = redissonClient.getLock("distributed:redissonLock");
                try{
                    lock.lock();
                    lock.lock();
                    stock --;
                    System.out.println(Thread.currentThread().getName() + "加锁成功:" + stock);
                } finally {
                    lock.unlock();
                    lock.unlock();
                    countDownLatch.countDown();
                }

            },"thread" + i).start();
        }
        countDownLatch.await();//阻塞，阻塞到所有加锁和解锁的过程释放掉
        System.out.println(stock);
    }
}
