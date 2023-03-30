package com.hero.controller;

import com.hero.bean.Purchase;
import com.hero.bean.Stock;
import com.hero.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/purchase/get/{pname}")
    public Purchase getPurchase(@PathVariable("pname") String pname) {
        Purchase purchase = purchaseService.getPurchaseByName(pname);
        String url = "http://msc-stock/stock/get/"+pname;
        Stock stock = restTemplate.getForObject(url, Stock.class);
        purchase.setStock(stock);
        return purchase;
    }

}
