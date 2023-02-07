package com.hero.spi;

import org.apache.dubbo.common.extension.Activate;

@Activate(group = "online", value = "alipay")
public class AlipayOrder implements Order {
    @Override
    public String way() {
        System.out.println("---  使用支付宝支付  ---");
        return "支付宝支付";
    }
}


