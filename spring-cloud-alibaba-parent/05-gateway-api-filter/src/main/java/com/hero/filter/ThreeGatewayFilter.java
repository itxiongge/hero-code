package com.hero.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//过滤器3
public class ThreeGatewayFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("pre-filter-【333】 ");
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            System.out.println("post-filter-【333】 ");
        }));
    }
}
