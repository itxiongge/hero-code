package com.hero.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stock {

    private Integer id;
    private String name;//库存名称
    private Integer total;//资源数量
}
