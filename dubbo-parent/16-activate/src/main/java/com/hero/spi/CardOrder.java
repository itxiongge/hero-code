package com.hero.spi;

import org.apache.dubbo.common.extension.Activate;

// order属性的默认值为0，order越小，其激活优先级越高，就越会先被激活
@Activate(group = {"online", "offline"}, order = 3)
public class CardOrder implements Order {
    @Override
    public String way() {
        System.out.println("---  使用银联卡支付  ---");
        return "银联卡支付";
    }
}
