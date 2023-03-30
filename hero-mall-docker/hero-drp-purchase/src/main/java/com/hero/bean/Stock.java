package com.hero.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    private Integer id;
    private String name;//库存名称
    private Integer total;//资源数量
}
