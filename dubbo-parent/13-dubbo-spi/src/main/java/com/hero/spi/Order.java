package com.hero.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * 下单接口
 */
@SPI("alipay")//指定默认的实现
public interface Order {
    // 支付方式
    String way();
}
