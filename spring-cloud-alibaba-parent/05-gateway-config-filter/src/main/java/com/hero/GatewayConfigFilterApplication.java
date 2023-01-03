package com.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayConfigFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayConfigFilterApplication.class, args);
    }
    //配置令牌桶算法的key:将主机名称作为限流key
    @Bean
    public KeyResolver keyResolver(){
        return exchange -> Mono.just(exchange
                .getRequest()
                .getRemoteAddress()
                .getHostName());
    }
}
