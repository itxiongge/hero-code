package com.hero.test;

import com.hero.spi.Order;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

public class DubboSPIAdaptiveMethodDemo {
    @Test
    public void test01() {
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);
        // 获取到order的自适应实例
        Order order = loader.getAdaptiveExtension();
        URL url = URL.valueOf("dubbo://localhost:8080/pay/xxx");
        System.out.println(order.pay(url));
        //System.out.println(order.way());// 报错
    }

    @Test
    public void test02() {
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);
        // 获取到order的自适应实例
        Order order = loader.getAdaptiveExtension();
        URL url = URL.valueOf("dubbo://localhost:8080/pay/xxx?order=alipay");
        System.out.println(order.pay(url));
        //System.out.println(order.way());// 报错
    }
}
