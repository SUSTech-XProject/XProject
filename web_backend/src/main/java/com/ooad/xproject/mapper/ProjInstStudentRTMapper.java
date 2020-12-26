package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.ProjInstStudentRT;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjInstStudentRTMapper {
    int deleteByPrimaryKey(Integer projInstRtId);

    int insert(ProjInstStudentRT record);

    int insertSelective(ProjInstStudentRT record);

    ProjInstStudentRT selectByPrimaryKey(Integer projInstRtId);

    int updateByPrimaryKeySelective(ProjInstStudentRT record);

    int updateByPrimaryKey(ProjInstStudentRT record);
}