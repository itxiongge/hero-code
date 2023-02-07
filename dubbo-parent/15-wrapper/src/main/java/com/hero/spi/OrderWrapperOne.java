package com.hero.spi;

import org.apache.dubbo.common.URL;

public class OrderWrapperOne implements Order {
    private Order order;

    public OrderWrapperOne(Order order) {
        this.order = order;
    }

    @Override
    public String way() {
        System.out.println("before-OrderWrapper111对way()的增强");
        String way = order.way();
        System.out.println("after-OrderWrapper111对way()的增强");
        return way;
    }

    @Override
    public String pay(URL url) {
        System.out.println("before-OrderWrapper111对pay()的增强");
        String pay = order.pay(url);
        System.out.println("after-OrderWrapper111对pay()的增强");
        return pay;
    }
}
