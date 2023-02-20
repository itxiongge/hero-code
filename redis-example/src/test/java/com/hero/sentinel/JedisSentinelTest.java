package com.hero.sentinel;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;


public class JedisSentinelTest {
    @Test
    public void testJedisSentinel() {
        Set<String> hosts = new HashSet<>();
        hosts.add("123.57.64.170:27001");
        hosts.add("123.57.64.170:27002");
        hosts.add("123.57.64.170:27003");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", hosts);
        Jedis jedis = jedisSentinelPool.getResource();
        jedis.set("hero", "666");
        String result = jedis.get("hero");
        System.out.println(result);
        jedisSentinelPool.close();
    }
}
