package com.hero.controller;

import com.hero.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    //接口的服务地址：协议，域名，端口，path
    private static final String payment_service = "http://localhost:8081";

    @GetMapping("/payment/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Integer id) {
        //这么做存在什么问题？
        //http://localhost:8081/payment/666
        /**
         * 问题01-消费者直接连接的提供者
         *   在order中，我们把url地址硬编码到了代码中，不方便后期维护
         *   order需要记忆payment-service的地址，如果出现变更，可能得不到通知，地址将失效
         *   order不清楚payment-service的状态，服务宕机也不知道
         *   payment-service只有1台服务，不具备高可用性，即便payment-service形成集群，order还需自己实现负载均衡
         */

        String url = String.format("%s/payment/%d", payment_service, id);

        Payment payment = restTemplate.getForObject(url, Payment.class);
        return ResponseEntity.ok(payment);
    }

}
