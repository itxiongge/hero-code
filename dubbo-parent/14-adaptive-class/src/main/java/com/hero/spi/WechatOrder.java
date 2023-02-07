package com.hero.spi;

public class WechatOrder implements Order {
    @Override
    public String way() {
        System.out.println("---  使用微信支付  ---");
        return "微信支付";
    }
}
