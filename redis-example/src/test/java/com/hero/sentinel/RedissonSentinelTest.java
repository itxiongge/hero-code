package com.hero.sentinel;

import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

public class RedissonSentinelTest {
    RedissonClient redissonClient;

    @Before
    public void init() {
        Config config = new Config();
        config.useSentinelServers().addSentinelAddress(
                "redis://123.57.64.170:27001",
                "redis://123.57.64.170:27002",
                "redis://123.57.64.170:27003"
                )
                .setMasterName("mymaster");
        config.setCodec(new JsonJacksonCodec());
        redissonClient = Redisson.create(config);

    }
    @Test
    public void redissonSentinel() {
        RBucket<String> hero = redissonClient.getBucket("hero");
        hero.set("你好，hero");
        String result = hero.get();
        System.out.println(result);
    }
}
