package com.hero.client;

import com.hero.pojo.User;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;


public class RedissonClientTest {
    RedissonClient redissonClient;

    @Before
    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://123.57.64.170:6379").setPassword("hero@123");
        config.setCodec(new JsonJacksonCodec());
        redissonClient = Redisson.create(config);

    }
    @Test
    public void redissonClient() {
        // 1. Create config object
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());
        System.out.println(config.getCodec());
        config.useSingleServer().setAddress("redis://123.57.64.170:6379").setPassword("hero@123");
        // 2. Create Redisson instance
        RedissonClient redisson = Redisson.create(config);

        // 3. Get Redis key
        RBucket<String> bucket = redisson.getBucket("hello");
        bucket.set("hero");
        String v = bucket.get();
        System.out.println(v);

        redisson.shutdown();
    }

    @Test
    public void redissonClient2() throws IOException {
        Config config = Config.fromYAML(new File("D:\\redis-example\\src\\main\\resources\\redisson-config.yaml"));
        RedissonClient redissonClient = Redisson.create(config);
        RBucket<String> bucket = redissonClient.getBucket("hello");
        bucket.set("hero");
        String s = bucket.get();
        System.out.println(s);
    }

    @Test
    public void testBucket() {
        RBucket<User> bucket = redissonClient.getBucket("hero");
        User user = new User(1,"hero", "18401790000", "北京");
        bucket.set(user);
        User u = bucket.get();
        System.out.println(u);
    }

    @Test
    public void testAtomicLong() {
        RAtomicLong store = redissonClient.getAtomicLong("store");
        store.set(10000);
        long num = store.decrementAndGet();
        System.out.println(num);

    }

    @Test
    public void testRMap() {
        RMap<String, Integer> heromap = redissonClient.getMap("heromap");
        heromap.put("key1", 1);
        heromap.put("key2", 2);
        heromap.put("key3", 3);
        heromap.put("key4", 4);
        //取数据
        Integer value1 = heromap.get("key1");
        System.out.println(value1);

    }

    @Test
    public void testBucketSync() {
        RBucket<String> hello = redissonClient.getBucket("hello");
        hello.set("hero");
        String o = hello.get();
        System.out.println(o);
    }

    @Test
    public void testBucketAsync() throws IOException {
        RAtomicLong apple = redissonClient.getAtomicLong("apple");
        RFuture<Long> future = apple.getAndDecrementAsync();

        //方式一
        future.onComplete((value,e)->{
            System.out.println(value);
        });
        //方式二
        future.thenAccept(r-> {
            System.out.println("thenAccept：" + r);
        });
        //方式三
        future.whenComplete((value, e)->{
            System.out.println("whenComplete：" + value);
        });
        //阻塞线程，等待返回结果
        System.in.read();

    }
}
