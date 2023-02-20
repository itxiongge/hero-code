package com.hero.cluster;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;


public class RedissonClusterTest {
    RedissonClient redissonClient;

    @Before
    public void init() {
        Config config = new Config();
        config.useClusterServers().addNodeAddress(
                "redis://123.57.64.170:7001",
                "redis://123.57.64.170:7002",
                "redis://123.57.64.170:7003",
                "redis://123.57.64.170:8001",
                "redis://123.57.64.170:8002",
                "redis://123.57.64.170:8003"
        );
        config.setCodec(new JsonJacksonCodec());
        redissonClient = Redisson.create(config);

    }
    @Test
    public void redissonSentinel() {
        RBucket<String> hero = redissonClient.getBucket("hero");
        hero.set("你好，雄哥");
        String result = hero.get();
        System.out.println(result);
    }
}
