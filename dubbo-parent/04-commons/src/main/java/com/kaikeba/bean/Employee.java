package com.kaikeba.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * company: www.kaikeba.com
 * Author: Rey
 */
@Data
public class Employee implements Serializable {
    private Integer id;
    private String name;
    private int age;
}
