package com.hero.service;


import com.hero.bean.Employee;

public interface EmployeeService {
    void addEmployee(Employee employee);
    Employee findEmployeeById(int id);
    Integer findEmployeeCount();
}
