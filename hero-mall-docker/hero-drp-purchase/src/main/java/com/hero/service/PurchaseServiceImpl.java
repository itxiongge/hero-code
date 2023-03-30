package com.hero.service;

import com.hero.bean.Purchase;
import com.hero.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @Author yaxiongliu
 **/
@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;

    @Override
    public boolean addPurchase(Purchase purchase) {
        Purchase p = purchaseRepository.findByName(purchase.getName());
        if (p == null) {
            purchaseRepository.save(purchase);
            return true;
        } else {
            p.setCount(p.getCount() + purchase.getCount());
            purchaseRepository.save(p);
        }
        return true;
    }

    @Override
    public Purchase getPurchaseByName(String name) {
        return purchaseRepository.findByName(name);
    }
}
