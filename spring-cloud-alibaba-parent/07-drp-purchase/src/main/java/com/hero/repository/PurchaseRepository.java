package com.hero.repository;

import com.hero.bean.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * @Author yaxiongliu
 **/
public interface PurchaseRepository extends JpaRepository<Purchase,Integer> {
    //根据名称查询库存
    Purchase findByName(String name);
}
