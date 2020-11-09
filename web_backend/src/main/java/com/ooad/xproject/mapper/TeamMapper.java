package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.Team;

public interface TeamMapper {
    int deleteByPrimaryKey(Integer teamId);

    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Integer teamId);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);
}