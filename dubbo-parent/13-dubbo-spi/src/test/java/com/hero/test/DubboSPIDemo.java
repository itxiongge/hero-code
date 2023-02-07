package com.hero.test;

import com.hero.spi.Order;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

public class DubboSPIDemo {

    @Test
    public void testSPI() {
        // 获取到SPI接口Order的loader实例
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);
        // 指定要加载并创建的扩展类实例
        Order alipay = loader.getExtension("alipay");
        System.out.println(alipay.way());
        Order wechat = loader.getExtension("wechat");
        System.out.println(wechat.way());
        Order defaultExtension = loader.getDefaultExtension();
        System.out.println(defaultExtension.way());
        // Order xxx = loader.getExtension("xxx");
        // System.out.println(xxx.way());
    }

    @Test
    public void testNull() {
        // 获取到SPI接口Order的loader实例
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);
        // 以下代码会报错
        Order alipay = loader.getExtension(null);
        System.out.println(alipay.way());
    }

}
