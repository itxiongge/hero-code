package com.hero.cluster;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;


public class LettuceClusterTest {
    @Test
    public void lettuceSentinelTest() {
        RedisURI node1 = RedisURI.create("redis://123.57.64.170:7001");
        RedisURI node2 = RedisURI.create("redis://123.57.64.170:7002");

        RedisClusterClient client = RedisClusterClient.create(Arrays.asList(node1, node2));
        //启用自适应群集拓扑视图更新
        ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enableAdaptiveRefreshTrigger(ClusterTopologyRefreshOptions.RefreshTrigger.MOVED_REDIRECT,
                        ClusterTopologyRefreshOptions.RefreshTrigger.PERSISTENT_RECONNECTS)
                .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(30))
                .build();

        client.setOptions(ClusterClientOptions.builder()
                .topologyRefreshOptions(topologyRefreshOptions)
                .build());

        StatefulRedisClusterConnection<String, String> connect = client.connect();
        RedisAdvancedClusterCommands<String, String> commands = connect.sync();
        String result = commands.get("hero");
        System.out.println(result);
        //关闭连接
        client.shutdown();
    }
}
