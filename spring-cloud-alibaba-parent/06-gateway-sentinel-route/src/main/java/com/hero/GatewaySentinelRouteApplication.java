package com.hero;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootApplication//一个顶三个：配置类
public class GatewaySentinelRouteApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaySentinelRouteApplication.class, args);
        initRule();//配置初始化网关的流控规则
        initBlockHandlers();// 路由限流降级方法
    }

    private static void initRule() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        GatewayFlowRule rule = gatewayFlowRule();
        rules.add(rule);
        GatewayRuleManager.loadRules(rules);
    }

    // 对名称为payment_route的路由规则进行限流
    private static GatewayFlowRule gatewayFlowRule() {
        // 定义一个Gateway限流规则实例
        GatewayFlowRule rule = new GatewayFlowRule();
        // 指定规则模式是route限流，其为默认值
        rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);
        // 指定sentienl资源名称为 路由规则id
        rule.setResource("payment_route");
        // QPS限流
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // QPS为2的时候限流
        rule.setCount(2);
        return rule;
    }
    // 路由限流降级方法
    private static void initBlockHandlers() {
        GatewayCallbackManager.setBlockHandler((exchange, th) -> {
            // 从请求中获取uri
            URI uri = exchange.getRequest().getURI();
            // 将响应数据写入到map
            Map<String, Object> map = new HashMap<>();
            map.put("uri", uri);
            map.put("msg", "访问量过大，请稍候重试");
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(map));
        });
    }
}
