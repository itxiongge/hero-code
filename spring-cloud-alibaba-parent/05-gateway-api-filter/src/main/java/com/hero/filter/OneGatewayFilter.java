package com.hero.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 目标：查看过滤器执行顺序，观察pre过滤和post过滤
 */
//过滤器1
public class OneGatewayFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取系统当前时间
        long startTime = System.currentTimeMillis();
        System.out.println("pre-filter-【111】 " + startTime);
        //设置filter过滤器时间
        exchange.getAttributes().put("startTime", startTime);
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            System.out.println("post-filter-【111】 ");
            //获取过滤器执行开始时间
            Long startTimeAttr = (Long) exchange.getAttributes().get("startTime");
            //获取过滤器执行结束时间
            long endTime = System.currentTimeMillis();
            //计算开始到结束时间差值
            System.out.println("OneGatewayFilter过滤器执行用时：" + (endTime - startTime));
        }));
    }
}

