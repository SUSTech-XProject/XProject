package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.Teacher;

public interface TeacherMapper {
    int deleteByPrimaryKey(Integer tchId);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer tchId);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);
}