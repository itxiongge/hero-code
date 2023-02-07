package com.hero.dao;

import com.hero.bean.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper   // 自动Mapper的动态代理对象
public interface EmployeeDao {
    void insertEmployee(Employee employee);
    Integer selectEmployeeCount();
    Employee selectEmployeeById(int id);
}
