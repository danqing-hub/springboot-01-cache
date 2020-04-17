package com.example.demo.service;

import com.example.demo.bean.Employee;
import com.example.demo.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author 丹青
 * @date 2020/4/6-13:19
 */
@Service
@CacheConfig(cacheNames = "emp")
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存；以后要是相同的数据，直接从缓存中获取，不用调用方法。
     * CacheManager管理多个Cache组件的，对缓存的真正的CRUD操作在Cache组件中，每一个缓存组件有自己唯一一个名字；
     * Cacheable的几个属性：
     *  cacheNames/value: 指定缓存组件的名字
     *  key:缓存数据使用的key；可以用它来指定。默认是使用方法参数的值
     *      编写可以适应SpEl表达式： #id；参数id的值
     *  keyGenerator: key的生成器；可以自己指定key的生成器的组件id
     *  key/keyGenerator 二选一使用
     *  cacheManager：指定缓存管理器
     *  condition：指定符合条件的情况下才缓存 eg:condition = "#id>0"
     *  unless：否定缓存：当unless指定的条件为true，方法的返回值就不会被缓存；（可以获取到结果进行判断）
     *          eg:unless = "#result==null"
     *  sync:是否使用异步模式
     * @param id
     * @return
     */
    /**
     *  Cacheable的执行流程：
 *      1.方法执行前，先去查询Cache（缓存组件），按照cacheNames指定的名字获取；
 *          （CacheManager先获取相应的缓存），第一次获取缓存，如果没有Cache组件就会自动创建
 *      2.去Cache中查找缓存的内容，使用一个key，默认就是方法的参数；
 *          key是按照某种策略生成的；默认是使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key
 *              SimpleKeyGenerator生成key的默认策略：
     *                 如果没有参数：key = new SimpleKey();
     *                 如果有一个参数：key = 参数值
     *                 如果有多个参数：key = newSimplekey(params);
 *      3.没有查到缓存就调用目标方法
 *      4.将目标方法返回的结果，放进缓存
 *
 *      总结：@Cacheable标注的方法执行之前先来检查缓存中有没有这个数据，默认按照参数的值作为
 *      key去查询缓存，如果没有就运行方法，并将结果放进缓存
 *
 *      核心：
     *      1）、使用CacheManager【ConcurrentMapCacheManager】按照名字得到Cache【ConcurrentMapCache】组件
     *      2）、key使用keyGenerator生成的，默认是SimpleKeyGenerator
     */
    @Cacheable(cacheNames= {"emp"}/*,keyGenerator = "myKeyGenerator",condition = "#id>0",unless = "#a0==2"*/)
    public Employee getEmp(Integer id){
        System.out.println("查询"+id+"号员工");
        Employee empById = employeeMapper.getEmpById(id);
        return empById;
    }

    /**
     * @CachePut
     *  既调用方法，又更新缓存数据
     *  修改了数据库的某个数据，同时更新缓存
     *
     *  执行逻辑：
     *      1.先调用目标方法
     *      2.将目标方法的结果缓存起来
     */
    @CachePut(value = "emp",key = "#result.id")
    public Employee UpdateEmp(Employee employee){
        System.out.println("updateEmp:"+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }


    /**
     * @CacheEvict:缓存清除
     *  key:指定清除的数据（默认为参数的值）
     *  allEntries：清除这个缓存中所有的数据
     *  beforeInvocation：缓存的清除是否在执行方法之前执行（默认是在方法执行之后执行）
     */
    @CacheEvict(value = "emp",key = "#id"/*,allEntries = true*/)
    public void deleteEmp(Integer id){
        System.out.println("deleteEmp"+id);
        employeeMapper.deleteEmp(id);
    }

    /**
     *   @Caching：定义复杂的缓存规则
     */
    @Caching(
            cacheable = {
                    @Cacheable(value = "emp",key = "#lastName")
                },
            put = {
                  @CachePut(value = "emp",key = "#result.id"),
                    @CachePut(value = "emp",key = "#result.email")
            }
            )
    public Employee getEmpByLastName(String lastName){
        return employeeMapper.getEmpByLastName(lastName);
    }
}
