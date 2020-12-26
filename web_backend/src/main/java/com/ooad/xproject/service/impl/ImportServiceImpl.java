package com.ooad.xproject.service.impl;

import com.ooad.xproject.bo.RecordUnitBO;
import com.ooad.xproject.bo.StudentClassBO;
import com.ooad.xproject.bo.StudentImportBO;
import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.entity.RecordInst;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.Student;
import com.ooad.xproject.entity.Teacher;
import com.ooad.xproject.mapper.ProjectMapper;
import com.ooad.xproject.mapper.RecordInstMapper;
import com.ooad.xproject.service.ImportService;
import com.ooad.xproject.service.PermissionService;
import com.ooad.xproject.service.RecordService;
import com.ooad.xproject.service.StudentService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Async
@Service
public class ImportServiceImpl implements ImportService {
    private final StudentService studentService;
    private final ProjectMapper projectMapper;
    private final RecordService recordService;
    private final PermissionService permissionService;
    private final RecordInstMapper recordInstMapper;

    public ImportServiceImpl(StudentService studentService, ProjectMapper projectMapper, RecordService recordService, PermissionService permissionService, RecordInstMapper recordInstMapper) {
        this.studentService = studentService;
        this.projectMapper = projectMapper;
        this.recordService = recordService;
        this.permissionService = permissionService;
        this.recordInstMapper = recordInstMapper;
    }

    @Override
    public void importStudentAc(Teacher teacher, List<StudentImportBO> studentImportBOList){
        for (StudentImportBO studentImportBO :
                studentImportBOList) {
            SvResult<Role> svResult = studentService.creatRoleAndStudent(teacher.getSchId(), studentImportBO);
            if (svResult.getData() != null) {
                permissionService.appendPmsRoleToNewRole(svResult.getData());
            }
        }
    }

    @Override
    public void importProjStd(int projId, List<StudentClassBO> studentClassBOList){
        for (StudentClassBO studentClassBO : studentClassBOList) {
            Student student = studentService.getStudentByStdNo(studentClassBO.getStdNo());
            if (student != null) {
                List<Integer> prrIdList = projectMapper.selectByProjAndRole(projId, student.getRoleId());
                if (prrIdList.size() == 0) {
                    projectMapper.insertProjectRoleRT(projId, student.getRoleId(), studentClassBO.getClsMark());
                } else {
                    projectMapper.updateProjectRoleRT(prrIdList.get(0), studentClassBO.getClsMark());
                }
            }
        }
    }

    @Override
    public void importRecordUnit(int modifier, int projId, List<RecordUnitBO> recordUnitBOList) {

        for (RecordUnitBO recordUnitBO : recordUnitBOList) {
            RecordInst recordInst = recordService.getRecordInstByUnit(recordUnitBO, projId);
            if (recordInst != null) {
                RecordInst recordInst1 = recordInstMapper.selectByRcdIdAndRoleId(recordInst.getRcdId(), recordInst.getRoleId());
                if (recordInst1 == null) {
                    recordInst.setComments(recordUnitBO.getComments());
                    recordInst.setContent(recordUnitBO.getGrade());
                    recordInst.setModifiedRoleId(modifier);
                    recordInstMapper.insertRecordInst(recordInst);
                } else {
                    recordInst1.setComments(recordUnitBO.getComments());
                    recordInst1.setContent(recordUnitBO.getGrade());
                    recordInst1.setModifiedRoleId(modifier);
                    recordInstMapper.updateRecordInst(recordInst1);
                }
            }
        }
    }
}
