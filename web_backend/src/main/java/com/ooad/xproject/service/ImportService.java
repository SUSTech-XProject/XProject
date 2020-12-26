package com.ooad.xproject.service;

import com.ooad.xproject.bo.RecordUnitBO;
import com.ooad.xproject.bo.StudentClassBO;
import com.ooad.xproject.bo.StudentImportBO;
import com.ooad.xproject.entity.Teacher;

import java.util.List;

public interface ImportService {
    void importRecordUnit(int modifier, int projId, List<RecordUnitBO> recordUnitBOList);

    void importStudentAc(Teacher teacher, List<StudentImportBO> studentImportBOList);

    void importProjStd(int projId, List<StudentClassBO> studentClassBOList);
}
