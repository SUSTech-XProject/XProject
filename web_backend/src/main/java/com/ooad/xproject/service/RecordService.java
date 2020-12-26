package com.ooad.xproject.service;

import com.ooad.xproject.bo.RecordUnitBO;
import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.entity.Record;
import com.ooad.xproject.entity.RecordInst;
import com.ooad.xproject.vo.*;

import java.util.List;

public interface RecordService {

    RecordInst getRecordInstByUnit(RecordUnitBO recordUnitBO, Integer projId);

    boolean createNewRecord(Integer roleId, RecordCreationVO recordCreationVO);

    List<Record> getRecordList(int projId);

    SvResult<Integer> deleteRecords(RecordDeletionVO recordDeletionVO);

    SvResult<RecordInstUpdateRetVO> updateRecordInsts(int roleId, RecordInstUpdateParamVO recordInstUpdateParamVO);

    SvResult<Integer> updateRecordInstsBatch(Integer roleId, CombineRcdInstParamVO combineRcdInstParamVO);

    Record getRecordByRcdId(int rcdId);
}
