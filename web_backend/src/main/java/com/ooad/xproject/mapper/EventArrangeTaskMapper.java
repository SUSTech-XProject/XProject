package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.EventArrangeTask;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventArrangeTaskMapper {
    int deleteByPrimaryKey(Integer eaTaskId);

    int insert(EventArrangeTask record);

    int insertSelective(EventArrangeTask record);

    EventArrangeTask selectByPrimaryKey(Integer eaTaskId);

    int updateByPrimaryKeySelective(EventArrangeTask record);

    int updateByPrimaryKey(EventArrangeTask record);

    List<EventArrangeTask> selectByProjId(Integer pid);
}