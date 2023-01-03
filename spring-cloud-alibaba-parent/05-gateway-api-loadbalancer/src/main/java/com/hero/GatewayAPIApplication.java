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
    //配置路由规则
    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder){
        //路由构建器对象，构建一个路由规则
        return builder.routes().route("loadbalancer_route",predicateSpec -> predicateSpec
                .path("/payment/**")
                .uri("lb://msc-payment")).build();//动态路由配置lb://{服务名称}
    }
}
