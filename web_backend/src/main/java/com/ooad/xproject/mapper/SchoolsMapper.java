package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.Schools;

public interface SchoolsMapper {
    int deleteByPrimaryKey(Integer schId);

    int insert(Schools record);

    int insertSelective(Schools record);

    Schools selectByPrimaryKey(Integer schId);

    int updateByPrimaryKeySelective(Schools record);

    int updateByPrimaryKey(Schools record);
}