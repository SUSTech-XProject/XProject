package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.Submission;

public interface SubmissionMapper {
    int deleteByPrimaryKey(Integer sbmId);

    int insert(Submission record);

    int insertSelective(Submission record);

    Submission selectByPrimaryKey(Integer sbmId);

    int updateByPrimaryKeySelective(Submission record);

    int updateByPrimaryKey(Submission record);
}