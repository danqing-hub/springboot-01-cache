package com.example.demo.controller;

import com.example.demo.bean.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 丹青
 * @date 2020/4/6-13:25
 */
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmp(@PathVariable("id") Integer id){
        Employee emp = employeeService.getEmp(id);
        return emp;
    }
    @GetMapping("/emp")
    public Employee update(Employee employee){
        Employee emp = employeeService.UpdateEmp(employee);
        return emp;
    }
    @GetMapping("/delemp")
    public String deleteEmp(Integer id){
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/emp/lastname/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName") String lastName){
        Employee emp = employeeService.getEmpByLastName(lastName);
        return emp;
    }
}
