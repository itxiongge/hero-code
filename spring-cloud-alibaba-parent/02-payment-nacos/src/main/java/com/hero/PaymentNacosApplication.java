package com.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class PaymentNacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentNacosApplication.class, args);
    }

}
