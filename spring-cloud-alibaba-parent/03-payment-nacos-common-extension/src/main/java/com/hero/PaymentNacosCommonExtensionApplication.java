package com.hero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class PaymentNacosCommonExtensionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentNacosCommonExtensionApplication.class, args);
    }

}
