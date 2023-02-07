package com.hero.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * 下单接口
 */
@SPI("wechat")
public interface GoodsOrder {
    String way();

    @Adaptive
    String pay(URL url);
}
