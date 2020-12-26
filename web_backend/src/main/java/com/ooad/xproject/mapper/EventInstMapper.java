package com.ooad.xproject.mapper;

import com.ooad.xproject.bo.EventInstBO;
import com.ooad.xproject.entity.EventInst;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventInstMapper {
    int deleteByPrimaryKey(Integer eventId);

    int insert(EventInst record);

    int insertSelective(EventInst record);

    EventInst selectByPrimaryKey(Integer eventId);

    int updateByPrimaryKeySelective(EventInst record);

    int updateByPrimaryKey(EventInst record);

    List<EventInst> selectByEATaskId(int eaTaskId);

    int countApplyNum(int eaTaskId, int projInstId);

    List<EventInstBO> selectInstBOByEATaskId(int eaTaskId);
}