package com.hero.controller;

import com.hero.bean.Purchase;
import com.hero.bean.Stock;
import com.hero.service.PurchaseService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private RestTemplate restTemplate;

    @GlobalTransactional
    @RequestMapping("/purchase/add/{deno}")
    public boolean addPurchaseHandle(@RequestBody Purchase purchase
            ,@PathVariable("deno") int deno) {
        purchaseService.addPurchase(purchase);

        int i = 3 / deno;

        Stock stock = Stock.builder()
                .name(purchase.getName())
                .total(purchase.getCount()).build();

        String url = "http://msc-stock/stock/add";
        Boolean result = restTemplate.postForObject(url, stock, Boolean.class);
        if (result != null) {
            return result;
        }
        return false;
    }

}
