package com.hero.controller;

import com.hero.entity.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RefreshScope//开启配置刷新
public class PaymentController {

    //获取端口信息
    @Value("${server.port}")
    private String port;
    //读取配置中心配置
    @Value("${depart.name}")
    private String departName;

    @GetMapping("/{id}")
    public ResponseEntity<Payment> payment(@PathVariable("id") Integer id) {
        Payment payment = new Payment(id,
                String.format("支付成功，payment{port:%s}{departName:%s}", port, departName));
        return ResponseEntity.ok(payment);
    }

}
