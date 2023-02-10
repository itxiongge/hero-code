package com.hero.controller;

import com.hero.bean.Stock;
import com.hero.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StockController {
    @Autowired
    private StockService stockService;

    @RequestMapping("/stock/add")
    public boolean addStockHandle(@RequestBody Stock stock) {
        return stockService.addStock(stock);
    }
}
