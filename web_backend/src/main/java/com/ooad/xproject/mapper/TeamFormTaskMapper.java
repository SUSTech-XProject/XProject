package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.TeamFormTask;

public interface TeamFormTaskMapper {
    int deleteByPrimaryKey(Integer tfTaskId);

    int insert(TeamFormTask record);

    int insertSelective(TeamFormTask record);

    TeamFormTask selectByPrimaryKey(Integer tfTaskId);

    int updateByPrimaryKeySelective(TeamFormTask record);

    int updateByPrimaryKey(TeamFormTask record);
}