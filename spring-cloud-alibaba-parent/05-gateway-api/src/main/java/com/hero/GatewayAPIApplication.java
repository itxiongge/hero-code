package com.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//一个顶三个：配置类
public class GatewayAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAPIApplication.class, args);
    }

    //配置路由规则
//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder builder){
//        return builder.routes()
//                .route("baidu_route",predicateSpec -> predicateSpec
//                .path("/**")
//                .uri("https://www.baidu.com")).build();
//    }

    //Path配置路由规则
    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_payment_route",ps -> ps.path("/payment/**")
                        .uri("http://localhost:8081"))
                .route("path_order_route",ps -> ps.path("/order/**")
                        .uri("http://localhost:8080"))
                .build();
    }
}
