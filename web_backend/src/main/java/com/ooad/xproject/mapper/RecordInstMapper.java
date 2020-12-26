package com.ooad.xproject.mapper;

import com.ooad.xproject.dto.GradeDTO;
import com.ooad.xproject.dto.RecordInstDTO;
import com.ooad.xproject.entity.RecordInst;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordInstMapper {
    int deleteByPrimaryKey(Integer rcdInstId);

    int insert(RecordInst record);

    int insertSelective(RecordInst record);

    RecordInst selectByPrimaryKey(Integer rcdInstId);

    int updateByPrimaryKeySelective(RecordInst record);

    int updateByPrimaryKey(RecordInst record);

    List<RecordInst> selectByRcdId(Integer rid);

    // extra

    List<RecordInstDTO> selectByProjAndStdRoleId(Integer projId, Integer stdRoleId);

    RecordInst selectByRcdIdAndRoleId(Integer rcdId, Integer roleId);

    int insertRecordInst(RecordInst recordInst);

    int updateRecordInst(RecordInst recordInst);

    List<GradeDTO> selectByProjInstIdAndRcdId(int projInstId, int rcdId);

    List<GradeDTO> selectByStdRoleIdAndRcdId(int stdRoleId, int rcdId);

    List<RecordInst> selectRIByRcdIdList(int[] rcdIdList);

    int generateRecordInst(Integer projId, Integer rcdId, Integer modifiedRoleId, int[] rcdIdList, double[] coeList);
}