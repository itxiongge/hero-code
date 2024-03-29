package com.hero.controller;

import com.hero.entity.Payment;
import com.hero.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    //获取端口信息
    @Value("${server.port}")
    private String port;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<Payment> payment(@PathVariable("id") Integer id) {
        Payment payment = new Payment(id, String.format("支付成功，payment{port:%s}", port));
        return ResponseEntity.ok(payment);
    }
    //新增
    @PostMapping("/save")
    public boolean savePayment(@RequestBody Payment payment) {
        payment.setMessage("新增成功");
        return true;
    }
    //根据id删除
    @DeleteMapping("/del/{id}")
    public boolean delete(@PathVariable("id") int id) {
        Payment payment = new Payment(id, "删除成功");
        return true;
    }
    //修改
    @PutMapping("/update")
    public boolean update(@RequestBody Payment payment) {
        payment.setMessage("修改成功");
        return true;
    }
    //列表查询
//    @GetMapping("/list")
//    public List<Payment> list() {
//        try {
//            TimeUnit.MILLISECONDS.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Payment payment = new Payment(1, "列表查询成功");
//        List<Payment> list = new ArrayList<>();
//        list.add(payment);
//        return list;
//    }
    @GetMapping("/list")
    public ResponseEntity<List> listHandle() {
        List<Payment> payments = paymentService.listPayment();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/all")
    public ResponseEntity<List> listAll() {
        List<Payment> payments = paymentService.listPayment();
        return ResponseEntity.ok(payments);
    }
}
