package com.hero.test;

import com.hero.spi.GoodsOrder;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

public class GoodsOrderTest {

    @Test
    public void test01() {
        ExtensionLoader<GoodsOrder> loader = ExtensionLoader.getExtensionLoader(GoodsOrder.class);
        GoodsOrder goodsOrder = loader.getAdaptiveExtension();
        URL url = URL.valueOf("dubbo://localhost:8080/pay/xxx");
        System.out.println(goodsOrder.pay(url));
    }

    @Test
    public void test02() {
        ExtensionLoader<GoodsOrder> loader = ExtensionLoader.getExtensionLoader(GoodsOrder.class);
        GoodsOrder goodsOrder = loader.getAdaptiveExtension();
        URL url = URL.valueOf("dubbo://localhost:8080/pay/xxx?goods.order=alipay");
        System.out.println(goodsOrder.pay(url));
    }
}
