package com.hero;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class OrderSentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderSentinelApplication.class, args);
        //initRule();//初始化熔断策略
        initFlowRule();//初始化流控规则
    }

    public static void initFlowRule() {
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule qpsRule = qpsFlowRule();
        flowRules.add(qpsRule);
        FlowRuleManager.loadRules(flowRules);
    }

    private static FlowRule qpsFlowRule() {
        //创建流控规则对象
        FlowRule qpsRule = new FlowRule();
        //设置流控资源名称
        qpsRule.setResource("getPaymentById");
        //设置流控规则【QPS和线程数】
        qpsRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置QPS数为1
        qpsRule.setCount(1);
        //值为default，表示对请求来源不做限定
        qpsRule.setLimitApp("default");
        return qpsRule;
    }

    public static void initRule() {
        List<DegradeRule> rules = new ArrayList<>();
        // 获取定义的规则
        //DegradeRule rule = slowRequestDegradeRule();
        DegradeRule errorRule = errorRatioDegradeRule();
        rules.add(errorRule);
        DegradeRuleManager.loadRules(rules);
    }
    //异常比例 熔断降级规则
    private static DegradeRule errorRatioDegradeRule() {
        DegradeRule rule = new DegradeRule();
        rule.setResource("getPaymentById");//服务资源名称要与被熔断降级的API接口资源名称保持一致
        // 指定熔断规则为 异常比例
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
        // 设置阈值：发生熔断的异常请求比例
        rule.setCount(0.5);
        rule.setTimeWindow(60);
        rule.setMinRequestAmount(5);
        return rule;
    }
    //慢调用比例 熔断降级规则
    public static DegradeRule slowRequestDegradeRule(){
        //创建一个降级规则实例
        DegradeRule rule = new DegradeRule();
        //设置配置熔断规则的资源名称
        rule.setResource("SlowRequestDegradeRule");
        //熔断策略：慢调用比例、异常比例、异常数
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        //设置阈值：RT的时间，单位毫秒。若一个请求获取到响应的时间超出该值，则会将该请求统计为“慢调用”
        rule.setCount(200);
        //熔断恢复时间窗口，单位秒
        rule.setTimeWindow(30);
        //可触发熔断的最小请求数，默认是5个
        rule.setMinRequestAmount(5);
        // 设置发生慢调用的比例
        rule.setSlowRatioThreshold(0.5);
        return rule;
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
