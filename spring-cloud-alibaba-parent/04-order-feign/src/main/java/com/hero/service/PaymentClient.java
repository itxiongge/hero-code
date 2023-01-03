package com.hero.service;

import com.hero.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FeignClient 注解作用：声明一个FeignClient客户端，参数为当前消费者要调用的提供者的微服务名称
 * Feign客户端接口定义：
 * 1.该接口名可以随意
 * 2.接口方法名可以随意
 * 3.方法参数及返回值必须要与提供者端相应方法相同
 * 在当前接口中采用SpringMVC注解
 */
@FeignClient(value = "msc-payment", fallback = PaymentClient.Fallback.class)
@RequestMapping("/payment")//抽取方法公共uri部分地址
public interface PaymentClient {

    @GetMapping("/{id}")
    ResponseEntity<Payment> payment(@PathVariable("id") int id);

//    @PostMapping("/save")
//    boolean savePayment(@RequestBody Payment payment);
//
//    @DeleteMapping("/del/{id}")
//    boolean removePaymentById(@PathVariable("id") int id);
//
//    @PutMapping("/update")
//    boolean modifyPayment(@RequestBody Payment payment);
//
//    @GetMapping("/list")
//    List<Payment> listPayment();

    @Component
    @RequestMapping("/fallback_payment")
    class Fallback implements PaymentClient {

        @Override
        public ResponseEntity<Payment> payment(int id) {
            Payment payment = new Payment(id, "熔断降级方法返回");
            return ResponseEntity.ok(payment);
        }
    }
}

