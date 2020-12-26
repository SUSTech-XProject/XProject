package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.School;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolMapper {
    int deleteByPrimaryKey(Integer schId);

    int insert(School record);

    int insertSelective(School record);

    School selectByPrimaryKey(Integer schId);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);

    List<School> selectAll();

    School selectBySchName(String schName);
}