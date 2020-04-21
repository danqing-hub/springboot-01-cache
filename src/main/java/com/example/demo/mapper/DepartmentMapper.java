package com.example.demo.mapper;

import com.example.demo.bean.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 丹青
 * @date 2020/4/5-19:32
 */
@Mapper
public interface DepartmentMapper {
    @Select("SELECT * FROM department WHERE id = #{id}")
    public Department getDeptById(Integer id);
}

