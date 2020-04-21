package com.example.demo;

import com.example.demo.bean.Employee;
import com.example.demo.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate; //操作k-v都是字符串的

    @Autowired
    RedisTemplate redisTemplate; //k-v都是对象

    //@Autowired
   // RedisTemplate empRedisTemplate;
    /**
     * Redis常见的五大数据类型：
     * String（字符串）、List（列表）、Set（集合）、Hash（散列）、Zset（有序集）
     * stringRedisTemplate.opsForValue()[String（字符串）]
     * stringRedisTemplate.opsForList()[List（列表）]
     * stringRedisTemplate.opsForSet()[Set（集合）]
     * stringRedisTemplate.opsForHash()[Hash（散列）]
     * stringRedisTemplate.opsForZset()[Zset（有序集）]
     */
    @Test
    public void test01(){
        //给redis中保存数据
        stringRedisTemplate.opsForValue().append("xx","cool");
        //读取数据
        stringRedisTemplate.opsForValue().get("xx");

        stringRedisTemplate.opsForList().leftPush("mylist1","1");
        stringRedisTemplate.opsForList().leftPush("mylist1","2");
    }

    @Test
    public void test02(){
        Employee empById = employeeMapper.getEmpById(1);
        //把查出的数据放到缓存，Employee需要序列化
        //默认如果保存对象，使用jdk序列化机制，序列化后的数据保存到redis中
        redisTemplate.opsForValue().set("emp-01",empById);
    }

    @Test
    public void test03(){
        Employee empById = employeeMapper.getEmpById(1);
        redisTemplate.opsForValue().set("emp-01",empById);
    }

    @Test
    void contextLoads() {
        Employee empById = employeeMapper.getEmpById(1);
        System.out.println(empById);
    }

}
