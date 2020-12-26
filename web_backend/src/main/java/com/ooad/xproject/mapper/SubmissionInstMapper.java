package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.SubmissionInst;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionInstMapper {
    int deleteByPrimaryKey(Integer sbmInstId);

    int insert(SubmissionInst record);

    int insertSelective(SubmissionInst record);

    SubmissionInst selectByPrimaryKey(Integer sbmInstId);

    int updateByPrimaryKeySelective(SubmissionInst record);

    int updateByPrimaryKey(SubmissionInst record);

    List<SubmissionInst> selectBySbmId(Integer sbmId);

    SubmissionInst selectBySbmIdAndProjInstId(Integer sbmId, Integer projInstId);

    List<SubmissionInst> selectBySbmIdAndProjId(Integer sbmId, Integer projId);

    int updateSubmissionInst(SubmissionInst record);
}