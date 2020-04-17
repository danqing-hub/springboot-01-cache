package com.example.demo;

import com.example.demo.bean.Employee;
import com.example.demo.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    EmployeeMapper employeeMapper;
    @Test
    void contextLoads() {
        Employee empById = employeeMapper.getEmpById(1);
        System.out.println(empById);
    }

}
