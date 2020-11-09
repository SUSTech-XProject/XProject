package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.SubmissionInst;

public interface SubmissionInstMapper {
    int deleteByPrimaryKey(Integer sbmInstId);

    int insert(SubmissionInst record);

    int insertSelective(SubmissionInst record);

    SubmissionInst selectByPrimaryKey(Integer sbmInstId);

    int updateByPrimaryKeySelective(SubmissionInst record);

    int updateByPrimaryKey(SubmissionInst record);
}