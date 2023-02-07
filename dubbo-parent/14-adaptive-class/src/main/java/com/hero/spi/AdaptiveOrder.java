package com.hero.spi;

import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.utils.StringUtils;

@Adaptive
public class AdaptiveOrder implements Order {
    private String defaultName;

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    @Override
    public String way() {
        ExtensionLoader<Order> loader = ExtensionLoader.getExtensionLoader(Order.class);
        Order order;
        if (StringUtils.isEmpty(defaultName)) {
            order = loader.getDefaultExtension();
        } else {
            order = loader.getExtension(defaultName);
        }
        return order.way();
    }
}
