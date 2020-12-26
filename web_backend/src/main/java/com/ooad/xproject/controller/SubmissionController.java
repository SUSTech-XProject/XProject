package com.ooad.xproject.controller;

import com.alibaba.fastjson.JSON;
import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.dto.SbmDTO;
import com.ooad.xproject.dto.StudentProjDTO;
import com.ooad.xproject.entity.*;
import com.ooad.xproject.service.*;
import com.ooad.xproject.utils.RoleUtils;
import com.ooad.xproject.vo.Result;
import com.ooad.xproject.vo.SbmInstVO;
import com.ooad.xproject.vo.SubmissionVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ooad.xproject.vo.Result.createBoolResult;

@RestController
public class SubmissionController {

    private final RoleService roleService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final HomeService homeService;
    private final SbmService sbmService;
    private final ProjectService projService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private final MailService mailService;

    public SubmissionController(RoleService testService, StudentService studentService, TeacherService teacherService, HomeService homeService, SbmService sbmService, ProjectService projService, MailService mailService) {
        this.roleService = testService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.homeService = homeService;
        this.sbmService = sbmService;
        this.projService = projService;
        this.mailService = mailService;
    }

    @ResponseBody
    @GetMapping("api/all/project/submission")
    public Result<?> getSbmListByProjId(@RequestParam(value="projId") int projId) {
        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        List<Submission> sbmList = sbmService.getSbmListByProjId(projId);
        List<SbmDTO> sbmDTOList = new ArrayList<>();
        for (Submission sbm: sbmList) {
            Teacher tch = teacherService.getTeacherByRoleId(sbm.getCreatorId());
            sbmDTOList.add(SbmDTO.builder()
                    .submission(sbm)
                    .teacher(tch)
                    .build());
        }
        logger.info("getSbmListByProjId -> " + Arrays.toString(sbmDTOList.toArray()));
        return new Result<>(sbmDTOList);
    }

    @ResponseBody
    @GetMapping("api/student/project/submission/inst")
    public Result<?> getSbmInstByProjId(@RequestParam("sbmId") int sbmId) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        Submission submission = sbmService.getSbmBySbmId(sbmId);
        // check project accessible
        if (!projService.isAccessible(role.getRoleId(), submission.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        SvResult<SubmissionInst> svResult = sbmService.getSbmInstByStdRoleIdAndSbmId(role.getRoleId(), sbmId);
        if (svResult.getData() == null) {
            return new Result<>(svResult.getMsg(), null);
        }
        Student submitter = studentService.getStudentByRoleId(role.getRoleId());

        SbmInstVO sbmInstVO = SbmInstVO.builder()
                .submissionInst(svResult.getData())
                .submitter(submitter)
                .build();
        return new Result<>(sbmInstVO);
    }

    @ResponseBody
    @PostMapping("api/teacher/project/sub/add")
    public Result<?> postAddSubmission(@RequestBody SubmissionVO submissionVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        // check project accessible
        if (!projService.isAccessible(role.getRoleId(), submissionVO.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        Submission sbm = new Submission();
        try {
            submissionVO.copyToSubmission(sbm, role.getRoleId());
        } catch (ParseException e) {
            e.printStackTrace();
            return new Result<>(RespStatus.FAIL, "Format is not correct");
        }
        logger.info("postAddSubmission -> " + sbm);
        boolean success = sbmService.createSubmission(sbm);

        if (success) {
            // send email to all students
            List<StudentProjDTO> stdList = projService.getStdProjList(submissionVO.getProjId());
            List<String> mailList = stdList.stream().map(StudentProjDTO::getEmail).collect(Collectors.toList());
            mailService.sendMailToStudent(mailList, "[XProject] New submission has been released",
                    "New submission has been released\r\n" +
                            "This automatic notification message was sent by Xproject");
        }

        return createBoolResult(success, "Create submission successfully", "Fail to create");
    }

    @ResponseBody
    @GetMapping("api/teacher/project/sub/delete")
    public Result<?> getDeleteSubmission(@RequestParam(value="sbmId") int sbmId) {
        Submission submission = sbmService.getSbmBySbmId(sbmId);
        if (submission == null) {
            return new Result<>(RespStatus.FAIL, "No such submission");
        }
        // check project accessible
        if (!projService.isAccessible(submission.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        boolean success = sbmService.deleteSubmission(sbmId);
        return createBoolResult(success, "Delete submission successfully", "Fail to delete");
    }

    @ResponseBody
    @PostMapping("api/teacher/project/sub/modify")
    public Result<?> postModifySubmission(@RequestBody SubmissionVO submissionVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        Submission submission = sbmService.getSbmBySbmId(submissionVO.getSbmId());
        if (submission == null) {
            return new Result<>(RespStatus.FAIL, "No such submission");
        }
        // check project accessible
        if (!projService.isAccessible(role.getRoleId(), submission.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        Submission sbm = submission;
        try {
            submissionVO.copyToSubmission(sbm, role.getRoleId());
        } catch (ParseException e) {
            e.printStackTrace();
            return new Result<>(RespStatus.FAIL, "Format is not correct");
        }
        logger.info("postModifySubmission -> " + sbm);
        System.out.println(JSON.toJSONString(sbm));
        System.out.println(JSON.toJSONString(submissionVO));
        boolean success = sbmService.modifySubmission(sbm);
        return createBoolResult(success, "Modify submission successfully", "Fail to modify");
    }

}
