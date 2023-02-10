package com.hero.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hero.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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
    //private static final String payment_service = "http://localhost:8081";
    //接口的服务地址：服务名称
    private static final String payment_service = "http://msc-payment";

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/load-balancer")
    public String loadBalancer() {
        ServiceInstance instance = loadBalancerClient.choose("msc-payment");
        return instance.getHost() + ":" + instance.getPort();
    }



    // 该注解表明当前方法是一个由Sentinel管理的资源，value属性用于指定该资源的名称

    /**
     * 降级的诱因：
     * 1.熔断
     * 2.超时
     * 3.异常
     * 4.限流
     * value属性作业指定当前资源的标识符
     */
//    @SentinelResource(value = "getPaymentById", fallback = "getHandlerFallback")
//    @GetMapping("/degrade/payment/{id}")
//    public ResponseEntity<Payment> getHandle(@PathVariable("id") int id) {
//        String url = payment_service + "/payment/" + id;
//        Payment payment = restTemplate.getForObject(url, Payment.class);
//        return ResponseEntity.ok(payment);
//    }
    //降级方法与被降级的方法关系：方法的参数与返回值必须是一致的，但是方法名称不可以一致！
    //指定服务降级处理方法
//    public ResponseEntity<Payment> getHandlerFallback(int id) {
//        Payment payment = Payment.builder().id(id).message("degrade-method-" + id).build();
//        return ResponseEntity.ok(payment);
//    }

    /**
     * fallbackClass 属性：指定服务降级处理类
     * fallback 属性：指定服务降级处理类中的方法
     */
//    @GetMapping("/degrade/payment/{id}")
//    @SentinelResource(value = "getPaymentById", fallback = "getFallback", fallbackClass = PaymentServiceFallBack.class)
//    public ResponseEntity<Payment> getHandle(@PathVariable("id") int id) {
////        if (id == 1) {
////            throw new RuntimeException("啊，我出错了");
////        }
//        String url = payment_service + "/payment/" + id;
//        Payment payment = restTemplate.getForObject(url, Payment.class);
//        return ResponseEntity.ok(payment);
//    }
//
//    @GetMapping("/degrade/payment/list")
//    @SentinelResource(fallback = "listFallback",fallbackClass = PaymentServiceFallBack.class)
//    public ResponseEntity<List> listHandle() {
//        String url = payment_service + "/payment/list/";
//        List list = restTemplate.getForObject(url, List.class);
//        return ResponseEntity.ok(list);
//    }
//    @GetMapping("/degrade/payment/list")
//    @SentinelResource(value = "SlowRequestDegradeRule", fallback = "listFallback", fallbackClass = PaymentServiceFallBack.class)
//    public ResponseEntity<List> listHandle() {
//        String url = payment_service + "/payment/list/";
//        List list = restTemplate.getForObject(url, List.class);
//        return ResponseEntity.ok(list);
//    }

    @GetMapping("/payment/{id}")
    @SentinelResource(value = "getPaymentById",blockHandler = "getHandlerBlock", fallback = "getHandlerFallback")
    public ResponseEntity<Payment> getHandle(@PathVariable("id") int id) {
        String url = payment_service + "/payment/" + id;
        Payment payment = restTemplate.getForObject(url, Payment.class);
        return ResponseEntity.ok(payment);
    }
    //定义限流被阻断处理方法，方法参数与返回值与被降级方法保持一致，参数列表中需多加一个BlockException异常参数
    public ResponseEntity<Payment> getHandlerBlock(int id, BlockException be) {
        Payment payment = Payment.builder().id(id).message("flow-control-" + id + "-" + be.getCause()).build();
        return ResponseEntity.ok(payment);
    }
//    @GetMapping("/degrade/payment/list")
//    @SentinelResource(value = "qpsFlowRule_list", fallback = "listFallback", fallbackClass = PaymentServiceFallBack.class)
//    public ResponseEntity<List> listHandle() {
//        String url = payment_service + "/payment/list/";
//        List list = restTemplate.getForObject(url, List.class);
//        return ResponseEntity.ok(list);
//    }


//    @GetMapping("/payment/{id}")
//    public ResponseEntity<Payment> getHandleCodeFlowControl(@PathVariable("id") int id) {
//        Entry entry = null;
//        try {
//            entry = SphU.entry("getPaymentById");
//
//            //....
//            String url = payment_service + "/payment/" + id;
//            Payment payment = restTemplate.getForObject(url, Payment.class);
//            return ResponseEntity.ok(payment);
//            //....
//
//        } catch (BlockException be) {
//            Payment payment = Payment.builder().id(id).message("code-flow-control-" + id + "-" + be.getCause()).build();
//            return ResponseEntity.ok(payment);
//        } finally {
//            if (entry != null) {
//                entry.exit();
//            }
//        }
//    }
}
