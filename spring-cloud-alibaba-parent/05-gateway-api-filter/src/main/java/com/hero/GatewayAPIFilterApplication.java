package com.hero;

import com.hero.filter.AddHeaderGatewayFilter;
import com.hero.filter.OneGatewayFilter;
import com.hero.filter.ThreeGatewayFilter;
import com.hero.filter.TwoGatewayFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayAPIFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAPIFilterApplication.class, args);
    }
//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("circuitBreaker_filter", ps -> ps.path("/**")
//                        .filters(fs -> fs.circuitBreaker(config -> {
//                            config.setName("myCircuitBreaker");
//                            config.setFallbackUri("forward:/fallback");
//                        }))
//                        .uri("http://localhost:8080"))
//                .build();
//    }
    //配置路由：自定义拦截器
//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
//        //路由构建器对象，构建一个路由规则
//        return builder.routes().route("AddHeader_route",predicateSpec -> predicateSpec
//                .path("/**")
//                .filters(gfs -> gfs.filter(new AddHeaderGatewayFilter()))
//                .uri("http://localhost:8888")).build();
//    }
//    //配置三个拦截器
//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
//        //路由构建器对象，构建一个路由规则
//        return builder.routes()
//                .route("custom_filter_route",ps -> ps.path("/**")
//                        .filters(gfs -> gfs
//                                .filter(new OneGatewayFilter())
//                                .filter(new TwoGatewayFilter())
//                                .filter(new ThreeGatewayFilter()))
//                        .uri("http://localhost:8888"))
//                .build();
//    }
    //配置全局权限校验过滤器
//    @Bean
//    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
//        //路由构建器对象，构建一个路由规则
//        return builder.routes()
//                .route("custom_global_filter_route",
//                        ps -> ps.path("/**")
//                        .uri("http://localhost:8888"))
//                .build();
//    }
    @Bean
    public RouteLocator someRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("circuitBreaker_filter", ps -> ps.path("/**")
                        .filters(fs -> fs.circuitBreaker(config -> {
                            config.setName("myCircuitBreaker");
                            config.setFallbackUri("forward:/fallback");
                        }))
                        .uri("http://localhost:8888"))
                .build();
    }
}
