package com.example.demo.service;

import com.example.demo.bean.Department;
import com.example.demo.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * @author 丹青
 * @date 2020/4/19-22:38
 */
@Service
@CacheConfig(cacheNames = "dept",cacheManager = "deptCacheManager")
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Qualifier("deptCacheManager")//明确指定
    @Autowired
    private CacheManager deptCacheManager;//注入部门的缓存管理器

   /* @Cacheable(cacheNames = "dept")
    public Department getDeptById(Integer id){
        System.out.println("查询部门"+id);
        Department deptById = departmentMapper.getDeptById(id);
        return deptById;
    }*/

    /**
     * 用编码的方式缓存:使用缓存管理器得到缓存，进行api调用
     * @param id
     * @return
     */
    public Department getDeptById(Integer id){
        System.out.println("查询部门"+id);
        Department deptById = departmentMapper.getDeptById(id);
        //拿到缓存，对编码增删改查
        Cache dept = deptCacheManager.getCache("dept");
        dept.put("dept:1",deptById);
        return deptById;
    }

}
