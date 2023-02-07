package com.hero.spi;

import org.apache.dubbo.common.extension.SPI;

@SPI("wechat")
public interface Order {
    String way();
}
