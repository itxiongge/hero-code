package com.hero.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 在自定义的 Filter 中为请求添加指定的请求头。
 */
public class AddHeaderGatewayFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.构建改变后的Request对象
        ServerHttpRequest request = exchange
                .getRequest()
                .mutate()
                .header("X-Request-red", "blue")
                .build();
        //2.将改变后的对象设置到exchange对象中
        ServerWebExchange webExchange = exchange.mutate().request(request).build();
        //3.设置修改后的exchange
        return chain.filter(webExchange);
    }
}
