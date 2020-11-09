package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.TeamGroup;

public interface TeamGroupMapper {
    int deleteByPrimaryKey(Integer tgpId);

    int insert(TeamGroup record);

    int insertSelective(TeamGroup record);

    TeamGroup selectByPrimaryKey(Integer tgpId);

    int updateByPrimaryKeySelective(TeamGroup record);

    int updateByPrimaryKey(TeamGroup record);
}