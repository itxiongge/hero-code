package com.hero.fallback;

import com.hero.entity.Payment;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

//服务降级处理类
public class PaymentServiceFallBack {

    public static ResponseEntity<Payment> getFallback(int id) {
        System.out.println("getHandle()执行异常 " + id);
        Payment payment = Payment.builder().id(id).message("degrade-class-" + id).build();
        return ResponseEntity.ok(payment);
    }

    public static ResponseEntity<List> listFallback() {
        System.out.println("listHandle()执行异常 ");
        List<Payment> list = new ArrayList<>();
        list.add(Payment.builder().message("no any Payment").build());
        return ResponseEntity.ok(list);
    }
}
