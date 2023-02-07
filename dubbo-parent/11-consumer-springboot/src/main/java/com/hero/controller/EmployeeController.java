package com.hero.controller;

import com.hero.bean.Employee;
import com.hero.service.EmployeeService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/consumer/employee")
public class EmployeeController {

    // @Autowired
    @Reference   // Dubbo的注解   <dubbo:reference />
    private EmployeeService employeeService;

    @PostMapping("/register")
    public String someHandle(Employee employee, Model model) {
        employeeService.addEmployee(employee);
        model.addAttribute("employee", employee);
        return "/welcome.jsp";
    }

    @RequestMapping("/find/{id}")
    @ResponseBody
    public Employee findHandle(@PathVariable("id") int id) {
        return employeeService.findEmployeeById(id);
    }

    @RequestMapping("/count")
    @ResponseBody
    public Integer countHandle() {
        return employeeService.findEmployeeCount();
    }

}
