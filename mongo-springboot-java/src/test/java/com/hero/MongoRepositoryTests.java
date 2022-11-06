package com.hero;


import com.hero.dao.EmployeeRepository;
import com.hero.pojo.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoRepositoryTests {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void add() {
        Employee employee = Employee.builder()
                .id("11").firstName("liu").lastName("hero").empId(1).salary(10200).build();
        employeeRepository.save(employee);
    }

    @Test
    public void findAll() {
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach(System.out::println);
    }

}
