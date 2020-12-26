package com.ooad.xproject.service.impl;

import com.ooad.xproject.bo.*;
import com.ooad.xproject.config.FileConfig;
import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.entity.*;
import com.ooad.xproject.mapper.ProjectMapper;
import com.ooad.xproject.mapper.RecordInstMapper;
import com.ooad.xproject.service.*;
import com.ooad.xproject.vo.Result;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class ExcelServiceImplTest {
    @Autowired
    private FileService fileService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordInstMapper recordInstMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private EATaskService eaTaskService;

    @Test
    void generateRecordUnitList() {
        String filePath = "C:\\Users\\BCS\\Desktop\\Testcase\\testImport\\OOADGrade.xlsx";
        List<RecordUnitBO> recordUnitList = projectService.getRecordUnitList(7);

        excelService.generate(recordUnitList, filePath);
    }

    @Test
    void generateStudentImportBOList() {

        String filePath = "C:\\Users\\BCS\\Desktop\\Testcase\\testImport\\StudentAcImport.xlsx";
        int clsFrom = 80, clsTo = 85; // 10 ~ 99
        int stdFrom = 10, stdTo = 70; // 10 ~ 99

        List<StudentImportBO> studentImportBOS = new ArrayList<>();
        for (int lab = clsFrom; lab <= clsTo; ++lab) {
            for (int i = stdFrom; i <= stdTo; ++i) {
                String stdId = "1181" + lab + i;
                StudentImportBO studentImportBO = new StudentImportBO();
                studentImportBO.setEmail(stdId + "@mail.fake.sustech.edu.cn");
                studentImportBO.setStdClass("18" + lab);
                studentImportBO.setStdNo(stdId);
                studentImportBO.setStdName("std" + lab + "_" + i);
                studentImportBO.setUsername("sustech" + stdId);
                studentImportBO.setPassword("sustech" + stdId);
                System.out.println(studentImportBO.getEmail());
                studentImportBOS.add(studentImportBO);
            }
        }

        System.out.println(excelService.generate(studentImportBOS, filePath));
    }

    @Test
    void generateStudentClassList() {
        FileConfig fileConfig = new FileConfig();
        String filePath = "C:\\Users\\BCS\\Desktop\\Testcase\\testImport\\studentClassImport.xlsx";
        List<StudentClassBO> studentClassBOList = new ArrayList<>();
        int clsFrom = 80, clsTo = 84; // 10 ~ 99
        int stdFrom = 10, stdTo = 70; // 10 ~ 99
        for (int lab = clsFrom; lab <= clsTo; ++lab) {
            for (int i = stdFrom; i <= stdTo; ++i) {
                String stdId = "1181" + lab + i;
                StudentClassBO studentClassBO = new StudentClassBO();
                studentClassBO.setClsMark("Lab " + lab % 10);
                studentClassBO.setStdNo(stdId);
                studentClassBOList.add(studentClassBO);
            }
        }

        excelService.generate(studentClassBOList, filePath);
    }

    @Test
    void getField() {
        Admin admin = new Admin();
        admin.setAdminId(1);
        admin.setRoleId(1);
        ExcelServiceImpl excelService = new ExcelServiceImpl(fileConfig, projectService, eaTaskService);

        String[] strings = excelService.getField(admin.getClass());
        System.out.println(Arrays.toString(strings));
    }

    @Test
    void importExcel() {
        ExcelServiceImpl excelService = new ExcelServiceImpl(fileConfig, projectService, eaTaskService);
        String filePath = "C:\\BCSpace\\JetProjects\\JavaProject\\xproject_backend\\business\\output\\output.xlsx";
        List<StudentImportBO> studentImportBOList = excelService.readStudentImportBO(filePath);
        System.out.println(studentImportBOList.toString());
    }


    @Test
    void postStudentAcCreationFromExcel() {

        String filePath = "C:\\BCSpace\\JetProjects\\JavaProject\\xproject_backend\\business\\output\\output.xlsx";
        System.out.println(filePath);
        List<StudentImportBO> studentImportBOList = excelService.readStudentImportBO(filePath);

        int successCnt = 0;
        for (StudentImportBO studentImportBO :
                studentImportBOList) {
            SvResult<Role> svResult = studentService.creatRoleAndStudent(1, studentImportBO);
            if (svResult.getData() != null) {
                successCnt++;
            }
        }
        RespStatus status = (successCnt == 0) ? RespStatus.FAIL : RespStatus.SUCCESS;
        String msg = (successCnt == 0) ? "Create Student Account fail" : "Create Student Account done";
        System.out.println(new Result<>(status, msg, successCnt).toString());
    }

    @Test
    void postRecordInstImportFromExcel() {
        Integer projId = 1;
        String filePath = "C:\\BCSpace\\JetProjects\\JavaProject\\xproject_backend\\business\\output.xlsx";
        List<RecordUnitBO> recordUnitBOList = excelService.readRecordUnitBO(filePath);

        int successCnt = 0;
        for (RecordUnitBO recordUnitBO : recordUnitBOList) {
            RecordInst recordInst = recordService.getRecordInstByUnit(recordUnitBO, projId);
            if (recordInst != null) {
                ++successCnt;
                RecordInst recordInst1 = recordInstMapper.selectByRcdIdAndRoleId(recordInst.getRcdId(), recordInst.getRoleId());
                if (recordInst1 == null) {
                    recordInst.setComments(recordUnitBO.getComments());
                    recordInst.setContent(recordUnitBO.getGrade());
                    recordInstMapper.insertRecordInst(recordInst);
                } else {
                    recordInst1.setComments(recordUnitBO.getComments());
                    recordInst1.setContent(recordUnitBO.getGrade());
                    recordInstMapper.updateRecordInst(recordInst1);
                }
            }
        }
        boolean check = (successCnt == 0);
        RespStatus status = (check) ? RespStatus.FAIL : RespStatus.SUCCESS;
        String msg = (check) ? "Upsert record fail" : "Upsert record done";
        System.out.println(new Result<>(status, msg, successCnt).toString());
    }


    @Test
    void postProjStdExcel() {
        Integer projId = 1;
        String filePath = "C:\\BCSpace\\JetProjects\\JavaProject\\xproject_backend\\business\\output.xlsx";
        List<StudentClassBO> studentClassBOList = excelService.readStudentClassBO(filePath);

        int successCnt = 0;
        for (StudentClassBO studentClassBO : studentClassBOList) {
            Student student = studentService.getStudentByStdNo(studentClassBO.getStdNo());
            if (student != null) {
                ++successCnt;
                List<Integer> prrIdList = projectMapper.selectByProjAndRole(projId, student.getRoleId());
                if (prrIdList.size() == 0) {
                    projectMapper.insertProjectRoleRT(projId, student.getRoleId(), studentClassBO.getClsMark());
                } else {
                    projectMapper.updateProjectRoleRT(prrIdList.get(0), studentClassBO.getClsMark());
                }
            }

        }
        boolean check = (successCnt == 0);
        RespStatus status = (check) ? RespStatus.FAIL : RespStatus.SUCCESS;
        String msg = (check) ? "Upsert student to project fail" : "Upsert student to project done";
        System.out.println(new Result<>(status, msg, successCnt).toString());
    }

    @Test
    void exportTeamByProjId() {
        SvResult<String> svResult = excelService.exportTeamByProjId(1);
        System.out.println(svResult.getData());
    }

    @Test
    void resourceUpload() {
        int projId, creatorId;
        projId = 1;
        creatorId = 1;

        byte[] cont = new byte[10];
        MultipartFile file = new MockMultipartFile("mockFile.txt", cont);

        System.out.println(fileService.uploadResource(file, projId, creatorId));


//        upload(file, filePath, file.getName());
    }

    @Test
    void exportEventInst() {
        SvResult<String> svResult = excelService.exportEventInst(11);
        System.out.println(svResult.getData());
    }
}