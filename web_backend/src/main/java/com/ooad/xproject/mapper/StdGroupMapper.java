package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.StdGroup;

public interface StdGroupMapper {
    int deleteByPrimaryKey(Integer sgpId);

    int insert(StdGroup record);

    int insertSelective(StdGroup record);

    StdGroup selectByPrimaryKey(Integer sgpId);

    int updateByPrimaryKeySelective(StdGroup record);

    int updateByPrimaryKey(StdGroup record);
}