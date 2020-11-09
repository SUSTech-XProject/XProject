package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.EventArrangeTask;

public interface EventArrangeTaskMapper {
    int deleteByPrimaryKey(Integer eaTaskId);

    int insert(EventArrangeTask record);

    int insertSelective(EventArrangeTask record);

    EventArrangeTask selectByPrimaryKey(Integer eaTaskId);

    int updateByPrimaryKeySelective(EventArrangeTask record);

    int updateByPrimaryKey(EventArrangeTask record);
}