package com.hero.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * 下单接口
 */
@SPI("wechat")
public interface Order {
    // 支持方式
    String way();

    @Adaptive
    String pay(URL url);
}
