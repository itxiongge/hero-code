package com.hero.client;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;

import java.util.List;


public class JedisClientTest {

    private JedisPool jedisPool;

    @Before
    public void init() {
        GenericObjectPoolConfig<Jedis> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(10);
        jedisPool = new JedisPool(config, "123.57.64.170", 6379);
    }

    @Test
    public void testJedis() {
        //Jedis jedis = new Jedis("123.57.64.170", 6379);
        Jedis jedis = new Jedis("redis://123.57.64.170:6379");
        jedis.auth("hero@123");

        jedis.set("hello", "hero");
        String hello = jedis.get("hello");
        System.out.println(hello);
        jedis.close();
    }

    @Test
    public void testJedisPool() {
        GenericObjectPoolConfig<Jedis> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(10);
        JedisPool jedisPool = new JedisPool(config, "123.57.64.170", 6379);
        // 从连接池中获得连接
        Jedis jedis = jedisPool.getResource();
        jedis.auth("hero@123");

        jedis.set("hello", "beauty");//短连接！
        String hello = jedis.get("hello");
        System.out.println(hello);
        //关闭jedis回收连接
        jedis.close();
        //系统关闭之前关闭jedisPool对象
        jedisPool.close();
    }

    @Test
    public void testJedisPoolNoClose() {
        for (int i = 0; i < 100; i++) {
            Jedis jedis = jedisPool.getResource();
            jedis.auth("hero@123");

            jedis.set("hello", "hero" + i);
            String hello = jedis.get("hello");
            System.out.println(hello);
            //关闭jedis回收连接
            jedis.close();
        }
    }

    @Test
    public void testJedisPipelineOne() {
        Jedis jedis = jedisPool.getResource();
        jedis.auth("hero@123");

        Pipeline p = jedis.pipelined();
        p.set("a", "1");
        p.set("b", "2");
        p.set("c", "3");

        p.hset("person", "id", "1001");
        p.hset("person", "name", "雄彦祖");
        p.hset("person", "address", "北京");

        Response<String> aResult = p.get("a");//Future未来任务
        Response<List<String>> personValues = p.hvals("person");
        //发送命令
        p.sync();
        //取返回结果，在sync()调用之前是没有结果的。
        String a = aResult.get();
        System.out.println(a);
        List<String> strings = personValues.get();
        System.out.println(strings);
    }

    @Test
    public void testJedisPipelineTwo() {
        long start = System.currentTimeMillis();
        Jedis jedis = jedisPool.getResource();
        jedis.auth("hero@123");

        Pipeline p = jedis.pipelined();
        for (int i = 0; i < 1000; i++) {
            String key = "hello-" + i;
            String value = "hero-" + i;
            p.set(key, value);
        }
        //发送命令
        List<Object> resultList = p.syncAndReturnAll();
        System.out.println("花费时间：" + (System.currentTimeMillis() - start) + " ms");
        //取返回结果
        System.out.println(resultList);
        jedis.close();
    }
    @Test
    public void testJedisPipelineTwoMatchedGroup() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Jedis jedis = jedisPool.getResource();
            jedis.auth("hero@123");

            jedis.set("hello"+ i, "hero" + i);
            String hello = jedis.get("hello" + i);
            System.out.println(hello);
            //关闭jedis回收连接
            jedis.close();
        }
        System.out.println("花费时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 订阅消息
     */
    @Test
    public void testSubscribe() {
        Jedis jedis = jedisPool.getResource();
        jedis.auth("hero@123");

        Listener listener = new Listener();
        System.out.println("client1 开启监听...");

        jedis.subscribe(listener, "hero");

        System.out.println("client1 结束监听。");
        jedis.close();
    }

    /**
     * 发布消息
     */
    @Test
    public void testPublish() {
        Jedis jedis = jedisPool.getResource();
        jedis.auth("hero@123");

        jedis.publish("hero", "Hello EveryOne！！！");
        jedis.close();
    }

    class Listener extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
            if ("hero".equals(channel)) {
                System.out.println(message);
            }
        }
    }
}
