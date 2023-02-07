package com.hero.consumer;

import com.hero.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerRegistersRun {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-consumer.xml");

        // 使用微信支付
        UserService wxPayService = (UserService) ac.getBean("bjWxPay");
        String wxPayResult = wxPayService.hello("来一斤砒霜");
        System.out.println(wxPayResult);

        // 使用支付宝支付
        UserService aliPayService = (UserService) ac.getBean("shAliPay");
        String aliPayResult = aliPayService.hello("来半斤西瓜霜");
        System.out.println(aliPayResult);
    }
}
