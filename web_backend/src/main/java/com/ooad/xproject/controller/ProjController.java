package com.ooad.xproject.controller;

import com.alibaba.fastjson.JSON;
import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.bo.TopicBO;
import com.ooad.xproject.bo.forming.FormingBO;
import com.ooad.xproject.bo.forming.FormingResultBO;
import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.dto.StudentProjDTO;
import com.ooad.xproject.entity.*;
import com.ooad.xproject.service.*;
import com.ooad.xproject.utils.RoleUtils;
import com.ooad.xproject.vo.*;
import org.apache.commons.math3.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ProjController {

    private final RoleService roleService;
    private final HomeService homeService;
    private final ProjectService projService;
    private final SubmissionInstService submissionInstService;
    private final ProjInstService projInstService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private final TeacherService teacherService;
    private final StudentService studentService;

    public ProjController(RoleService roleService, HomeService homeService, ProjectService projService, SubmissionInstService submissionInstService, ProjInstService projInstService, TeacherService teacherService, StudentService studentService) {
        this.roleService = roleService;
        this.homeService = homeService;
        this.projService = projService;
        this.submissionInstService = submissionInstService;
        this.projInstService = projInstService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @ResponseBody
    @GetMapping("api/all/project/topic")
    public Result<?> getProjTopics(@RequestParam(value="projId") int projId) {

        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        Project project = projService.getProject(projId);
        List<TopicBO> topicList = JSON.parseArray(project.getTopics(), TopicBO.class);
        return new Result<>(topicList);
    }

    @ResponseBody
    @GetMapping("api/all/project/overview")
    public Result<Project> getProject(@RequestParam("projId") int projId) {
        logger.info("getProject");

        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        Project proj = projService.getProject(projId);

        return new Result<>(proj);
    }

    @ResponseBody
    @PostMapping("api/teacher/update-overview")
    public Result<Boolean> updateProject(@RequestBody ProjectVO projectVO) {
        logger.info("updateProject");
        // check project accessible
        if (!projService.isAccessible(projectVO.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        Project project = new Project();
        projectVO.copyToProjUpdate(project);
        boolean success = projService.updateProject(project);

        if (success) {
            return new Result<>(true);
        } else {
            return new Result<>(RespStatus.FAIL, "Update failed", false);
        }
    }


    @ResponseBody
    @GetMapping("api/teacher/project/students")
    public Result<?> getProjStdList(@RequestParam(value="projId") int projId) {
        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        List<StudentProjDTO> stdProjDTOList = projService.getStdProjList(projId);
//        logger.info(String.format("getProjStdList -> %s", stdProjDTOList));
        return new Result<>(stdProjDTOList);
    }

    @ResponseBody
    @PostMapping("api/teacher/team/auto-forming")
    public Result<?> postAutoForming(@RequestBody AutoFormingVO autoFormingVO) {
        // no check access

        FormingBO formContext = new FormingBO();
        boolean success = formContext.setStrategy(autoFormingVO.getStrategy());
        if (!success) {
            return new Result<>(RespStatus.FAIL, "No strategy named " + autoFormingVO.getStrategy());
        }
        formContext.setProjInstList(autoFormingVO.getProjInstList());
        formContext.setStdRoleIdList(autoFormingVO.getStdRoleIdList());

        SvResult<FormingResultBO> formingRes = projService.autoForming(formContext);
        FormingResultBO res = formingRes.getData();

        // handle auto confirm
        String autoConfirmMsg = null;
        if (autoFormingVO.isAutoConfirm()) {
            List<Pair<Integer, Integer>> matchList = res.getMatchList();
            Set<Integer> projInstIdSet = matchList.stream()
                    .mapToInt(Pair::getFirst)
                    .collect(HashSet::new, (set, item) -> ((Set<Integer>) set).add(item), null);
            int[] projInstIdList = projInstIdSet.stream().mapToInt(Integer::valueOf).toArray();
            autoConfirmMsg = projInstService.confirmBatchTch(projInstIdList, false);
        }

//        logger.info(String.format("getProjStdList -> %s", stdProjDTOList));
        res.setMatchList(null);
        String retMsg = formingRes.getMsg() + ((autoConfirmMsg == null) ? "" : "\r\n" + autoConfirmMsg);
        return new Result<>(retMsg, res);
    }

    @ResponseBody
    @GetMapping("api/teacher/project/submission-ins")
    public Result<?> getSbmInsList(@RequestParam(value="sbmId") int sbmId, @RequestParam(value="projId") int projId){
        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        List<SubmissionInst> submissionInsts = submissionInstService.getSubmissionInstList(sbmId, projId);
        List<SbmInstVO> sbmInstVOS = new ArrayList<>();
        for (SubmissionInst submissionInst : submissionInsts) {
            Student submitter = studentService.getStudentByRoleId(submissionInst.getSubmitterId());

            SbmInstVO sbmInstVO = SbmInstVO.builder()
                    .submissionInst(submissionInst)
                    .submitter(submitter)
                    .build();
            sbmInstVOS.add(sbmInstVO);
        }
        return new Result<>(sbmInstVOS);
    };

    @ResponseBody
    @GetMapping("api/student/team/ungrouped")
    public Result<?> getUngroupedStudents(@RequestParam(value="projId") int projId) {
        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        List<StudentProjDTO> stdProjDTOList = projService.getStdProjList(projId);
        stdProjDTOList.removeIf(studentProjDTO -> studentProjDTO.getProjInstId() != null);
//        logger.info(String.format("getUngroupedStudents -> %s", stdProjDTOList));
        return new Result<>(stdProjDTOList);
    }

    @ResponseBody
    @GetMapping("api/all/team/stu-proj")
    public Result<?> getStuProj(@RequestParam(value="roleId") int roleId) {
        // no check school
        List<Project> projList = homeService.getProjectList(roleId);
        logger.info(String.format("getUngroupedStudents -> %s", projList));
        return new Result<>(projList);
    }

    @ResponseBody
    @PostMapping("api/teacher/project/add")
    public Result<?> postAddProject(@RequestBody ProjectVO projectVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        Project proj = new Project();
        projectVO.copyToProjCreate(proj);
        proj.setCreatorId(role.getRoleId());
        Teacher teacher = teacherService.getTeacherByRoleId(role.getRoleId());
        proj.setSchId(teacher.getSchId());
        boolean success = projService.createProject(proj);
        return Result.createBoolResult(success, "Create project successfully", "Create project failed");
    }

    @ResponseBody
    @PostMapping("api/teacher/project/delete")
    public Result<?> postDeleteProject(@RequestParam("projId") int projId) {
        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        boolean success = projService.deleteProject(projId);
        return Result.createBoolResult(success, "Delete project successfully", "Delete project failed");
    }

    @ResponseBody
    @PostMapping("api/teacher/project/add/std")
    public Result<?> postAddStdIntoProj(@RequestBody ProjAddStdVO projAddStdVO) {

        // check project accessible
        if (!projService.isAccessible(projAddStdVO.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        int successCnt = 0;
        for (int stdRoleId: projAddStdVO.getStdRoleIdList()) {
            boolean success = homeService.joinProject(stdRoleId, projAddStdVO.getProjId(), projAddStdVO.getGroupMark());
            successCnt += success ? 1 : 0;
        }

        return new Result<>(successCnt);
    }
}
