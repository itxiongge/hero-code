package com.hero;

import com.hero.pojo.Employee;
import com.mongodb.client.result.UpdateResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * 注意：不管使用什么框架，都不要记忆！
 * 套路：Copy form document！
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTemplateTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void add() {
        Employee employee = Employee.builder()
                .id("22").firstName("wang").lastName("benson").empId(2).salary(12200).build();
        mongoTemplate.save(employee);
    }

    @Test
    public void findAll() {
        List<Employee> employees = mongoTemplate.findAll(Employee.class);
        employees.forEach(System.out::println);
    }

    @Test
    public void findById() {
        Employee employee = Employee.builder().id("11").build();

        Query query = new Query(where("id").is(employee.getId()));

        List<Employee> employees = mongoTemplate.find(query, Employee.class);

        employees.forEach(System.out::println);
    }

    @Test
    public void findByName() {

        Employee employee = Employee.builder().lastName("hero").build();

        Query query2 = new Query(where("lastName").regex("^.*" + employee.getLastName() + ".*$"));

        List<Employee> empList = mongoTemplate.find(query2, Employee.class);

        empList.forEach(System.out::println);

    }

    @Test
    public void update() {
        Employee employee = Employee.builder().id("11").build();
        //使用更新的文档更新所有与查询文档条件匹配的对象
        Query query = new Query(where("id").is(employee.getId()));

        UpdateDefinition updateDefinition = new Update().set("lastName", "hero110");

        UpdateResult updateResult = mongoTemplate.updateMulti(query, updateDefinition, Employee.class);

        System.out.println("update id:{}" + updateResult.getUpsertedId());
    }

    @Test
    public void del() {
        Employee employee = Employee.builder().lastName("hero110").build();

        Query query = new Query(where("lastName").is(employee.getLastName()));

        mongoTemplate.remove(query, Employee.class);
    }
}
