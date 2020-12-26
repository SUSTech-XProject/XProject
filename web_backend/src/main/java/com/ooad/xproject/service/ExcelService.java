package com.ooad.xproject.service;

import com.ooad.xproject.bo.RecordUnitBO;
import com.ooad.xproject.bo.StudentClassBO;
import com.ooad.xproject.bo.StudentImportBO;
import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.entity.Admin;
import com.ooad.xproject.entity.Student;
import com.ooad.xproject.vo.Result;

import java.lang.reflect.Field;
import java.util.List;

public interface ExcelService {
    SvResult<String> generate(List<?> objectList, String filePath, String[] fields, String[] titles);

    SvResult<String> generate(List<?> objectList, String filePath, String[] fields);

    SvResult<String> generate(List<?> objectList, String filePath);

    List<StudentImportBO> readStudentImportBO(String filePath);

    List<RecordUnitBO> readRecordUnitBO(String filePath);

    List<StudentClassBO> readStudentClassBO(String filePath);

    SvResult<String> exportRecordUnitByProjId(Integer projId);

    SvResult<String> exportTeamByProjId(Integer projId);

    SvResult<String> exportEventInst(Integer eaId);
}
