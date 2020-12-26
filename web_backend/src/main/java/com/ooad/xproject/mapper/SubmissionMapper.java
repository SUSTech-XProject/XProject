package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.Submission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionMapper {
    int deleteByPrimaryKey(Integer sbmId);

    int insert(Submission record);

    int insertSelective(Submission record);

    Submission selectByPrimaryKey(Integer sbmId);

    int updateByPrimaryKeySelective(Submission record);

    int updateByPrimaryKey(Submission record);

    List<Submission> selectByProjId(Integer pid);
}