package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 搭建基本环境
 * 1、导入数据库文件
 * 2、创建javaBean 封装数据
 * 。。。。。。
 * 缓存默认使用的是ConcurrentMapCacheManager创建的ConcurrentMapCache组件，将数据保存在ConcurrentMap<Object, Object>中。
 * 开发中经常使用缓存中间件：redis、memcached、ehcache；
 *
 */
@MapperScan("com.example.demo.mapper")
@SpringBootApplication
@EnableCaching
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
