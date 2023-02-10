package com.hero;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
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
public class GatewaySentinelApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaySentinelApiApplication.class, args);
        initCustomizedApis();//初始化自定义api
        initRule();//配置初始化网关的流控规则
        initBlockHandlers();//初始化路由限流降级处理方法
    }
    private static void initCustomizedApis() {
        // 定义一个名称为 order_api 的路由api
        ApiDefinition departApi = new ApiDefinition("order_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/order/get/**")
                            // 指定该路由api对于请求的匹配策略为 前辍匹配
                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});

        // 定义一个名称为 payment_api 的路由api
        ApiDefinition staffApi = new ApiDefinition("payment_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/payment/get/2"));
                    add(new ApiPathPredicateItem().setPattern("/payment/get/3")
                            // 指定该路由api对于请求的匹配策略为 精确匹配
                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_EXACT));
                }});

        Set<ApiDefinition> definitions = new HashSet<>();
        definitions.add(departApi);
        definitions.add(staffApi);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }
    private static void initRule() {
        Set<GatewayFlowRule> rules = new HashSet<>();
        // 在这里指定了对哪些路由api进行限流
        // 这里仅对/order/get/**形式的请求进行限流
        GatewayFlowRule orderRule = gatewayFlowRule("order_api", 1);
        // 这里仅对/payment/get/2与/payment/get/3这两个请求进行限流
        GatewayFlowRule paymentRule = gatewayFlowRule("payment_api", 2);
        rules.add(orderRule);
        rules.add(paymentRule);
        GatewayRuleManager.loadRules(rules);
    }


    //定义网关限流规则
    private static GatewayFlowRule gatewayFlowRule(String apiName, int count) {
        GatewayFlowRule rule = new GatewayFlowRule();
        // 指定规则模式为路由api限流
        rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME);
        rule.setResource(apiName);//api名称
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(count);
        return rule;
    }

    // 降级处理方法
    private static void initBlockHandlers() {
        GatewayCallbackManager.setBlockHandler((exchange, th) -> {
            URI uri = exchange.getRequest().getURI();
            Map<String, Object> map = new HashMap<>();
            map.put("uri", uri);
            map.put("msg", "访问量过大，请稍候重试");
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(map));
        });
    }
}
