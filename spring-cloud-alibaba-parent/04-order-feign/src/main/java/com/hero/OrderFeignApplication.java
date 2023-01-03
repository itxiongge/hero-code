package com.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.hero.service")
public class OrderFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderFeignApplication.class, args);
    }

    /**
     * Spring提供了一个RestTemplate模板工具类，对基于Http的客户端进行了封装，并且实现了对象与json的序列化和反序列化，非常方便。
     * RestTemplate并没有限定Http的客户端类型，而是进行了抽象
     * 目前常用的3种都有支持：HttpClient、OkHttp、JDK原生的URLConnection（默认的）
     */
    @Bean
    @LoadBalanced//开启负载均衡器
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
