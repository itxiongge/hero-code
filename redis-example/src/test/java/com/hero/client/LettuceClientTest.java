package com.hero.client;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class LettuceClientTest {

    private RedisClient redisClient;
    // connection 是线程安全的
    private StatefulRedisConnection<String, String> connection;
    @Before
    public void init() {
        redisClient = RedisClient.create(RedisURI.builder()
                .withHost("123.57.64.170")
                .withPort(6379)
                .withPassword("hero@123".toCharArray())
                .build());

        connection = redisClient.connect();
        RedisAsyncCommands<String, String> async = connection.async();
        RedisCommands<String, String> sync = connection.sync();
    }

    @Test
    public void testLettuceSyncClient() {
        // URI中包含数据库
        // RedisClient redisClient = RedisClient.create("redis://123.57.64.170:6379/0");
        // RedisClient redisClient = RedisClient.create("redis://123.57.64.170:6379/0");
        // 默认使用0号数据库
        // RedisClient redisClient = RedisClient.create("redis://123.57.64.170:6379");

        // 使用RedisURI构建请求地址
        RedisClient redisClient = RedisClient.create(RedisURI.builder()
                .withHost("123.57.64.170")
                .withPort(6379)
                .withPassword("hero@123".toCharArray())
                .build());

        // 创建连接
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        // 创建同步命令对象
        RedisCommands<String, String> syncCommands = connection.sync();
        // 执行Redis命令
        syncCommands.set("hello", "Hello, Hero!!!");
        String value = syncCommands.get("hello");
        System.out.println(value);
        // 关闭连接
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void testLettuceAsyncClient() throws IOException {
        RedisAsyncCommands<String, String> async = connection.async();
        RedisFuture<String> future1 = async.set("hello", "hello lettuce");
        future1.thenAccept(r-> System.out.println(r));
        RedisFuture<String> future2 = async.get("hello");
        future2.thenAccept(value-> System.out.println(value));
        System.out.println("我先输出");
        System.in.read();
    }

    @Test
    public void testLettucePipeline() {
        RedisAsyncCommands<String, String> async = connection.async();
        List<RedisFuture<?>> futureList = new ArrayList<>();
        // 停止自动提交命令
        async.setAutoFlushCommands(false);
        for (int i = 0; i < 1000; i++) {
            String key = "hello" + i;
            String value = "hero" + i;
//            RedisFuture<String> future = async.set(key, value);
            RedisFuture<Long> future = async.del(key);
            futureList.add(future);
        }
        long start = System.currentTimeMillis();
        //批量提交命令
        async.flushCommands();
        boolean result = LettuceFutures.awaitAll(10, TimeUnit.SECONDS, futureList.toArray(new RedisFuture[0]));
        System.out.println("花费时间：" + (System.currentTimeMillis() - start) + " ms");
        System.out.println(result);
    }


}
