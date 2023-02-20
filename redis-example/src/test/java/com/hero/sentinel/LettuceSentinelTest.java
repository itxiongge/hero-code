package com.hero.sentinel;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;

public class LettuceSentinelTest {
    @Test
    public void lettuceSentinelTest() {
        RedisURI redisUri = RedisURI.Builder
                .sentinel("123.57.64.170",27001, "mymaster")
                .withSentinel("123.57.64.170", 27002)
                .withSentinel("123.57.64.170", 27003)
                .build();
        RedisClient client = RedisClient.create(redisUri);

        StatefulRedisConnection<String, String> connect = client.connect();
        RedisCommands<String, String> commands = connect.sync();
        String result = commands.get("hero");
        System.out.println(result);
        //关闭连接
        client.shutdown();
    }
}
