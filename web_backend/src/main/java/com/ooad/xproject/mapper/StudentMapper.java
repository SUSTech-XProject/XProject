package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper {
    int deleteByPrimaryKey(Integer stdId);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer stdId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}