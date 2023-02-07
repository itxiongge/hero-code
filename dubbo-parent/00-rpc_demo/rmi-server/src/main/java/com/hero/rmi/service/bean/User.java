package com.hero.rmi.service.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {//注意：实体类实现Serializable接口

    private String name;
    private int age;
    private String sex;
}
