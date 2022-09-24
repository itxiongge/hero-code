package com.hero.rpc.producer.impl;

import com.hero.rpc.producer.SkuService;

public class SkuServiceImpl implements SkuService {
    @Override
    public String findByName(String name) {
        return "sku{}:" + name;
    }
}
