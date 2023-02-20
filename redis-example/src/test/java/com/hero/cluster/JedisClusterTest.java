package com.hero.cluster;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;


public class JedisClusterTest {
    @Test
    public void testJedisCluster() {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("123.57.64.170", 7001));
        nodes.add(new HostAndPort("123.57.64.170", 7002));
        nodes.add(new HostAndPort("123.57.64.170", 7003));
        nodes.add(new HostAndPort("123.57.64.170", 8001));
        nodes.add(new HostAndPort("123.57.64.170", 8002));
        nodes.add(new HostAndPort("123.57.64.170", 8003));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("hero", "999");
        String hero = jedisCluster.get("hero");
        System.out.println(hero);
        jedisCluster.close();
    }
}
