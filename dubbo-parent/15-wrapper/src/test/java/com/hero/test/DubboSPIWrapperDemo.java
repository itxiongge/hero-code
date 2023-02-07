package com.hero.test;

import com.hero.spi.Order;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

import java.util.Set;

public class DubboSPIWrapperDemo {

    @Test
    public void test01() {
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);
        Order order = loader.getAdaptiveExtension();
        URL url = URL.valueOf("dubbo://localhost:8080/pay/xxx");
        System.out.println(order.pay(url));
        // System.out.println(order.way());
    }

    @Test
    public void test02() {
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);
        Order order = loader.getAdaptiveExtension();
        URL url = URL.valueOf("dubbo://localhost:8080/pay/xxx?order=alipay");
        System.out.println(order.pay(url));
    }

    // Wrapper类不是直接扩展类
    @Test
    public void test03() {
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);
        // 获取该SPI接口的所有直接扩展类：即该扩展类是直接对该SPI接口进行业务功能上的扩展，可以单独使用
        Set<String> extensions = loader.getSupportedExtensions();
        System.out.println(extensions);
    }
}
