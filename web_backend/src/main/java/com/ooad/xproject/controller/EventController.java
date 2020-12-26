package com.ooad.xproject.controller;

import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.dto.EATaskDTO;
import com.ooad.xproject.dto.EventInstDTO;
import com.ooad.xproject.dto.StudentDTO;
import com.ooad.xproject.dto.StudentProjDTO;
import com.ooad.xproject.entity.*;
import com.ooad.xproject.service.*;
import com.ooad.xproject.utils.RoleUtils;
import com.ooad.xproject.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ooad.xproject.constant.ProjInstStatus.Confirm;
import static com.ooad.xproject.vo.Result.createBoolResult;

@RestController
public class EventController {

    private final RoleService roleService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final HomeService homeService;
    private final ProjInstService projInstService;
    private final EATaskService eaTaskService;
    private final ProjectService projService;
    private final MailService mailService;

    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    public EventController(RoleService roleService, StudentService studentService, TeacherService teacherService, HomeService homeService, ProjInstService projInstService, EATaskService eaTaskService, ProjectService projService, MailService mailService) {
        this.roleService = roleService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.homeService = homeService;
        this.projInstService = projInstService;
        this.eaTaskService = eaTaskService;
        this.projService = projService;
        this.mailService = mailService;
    }


    @ResponseBody
    @GetMapping("api/all/event")
    public Result<?> getEATaskList(@RequestParam("projId") int projId) {
        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        List<EventArrangeTask> eaTaskList = eaTaskService.getEATaskList(projId);
        List<EATaskDTO> eaTaskDTOList = new ArrayList<>();
        for (EventArrangeTask eaTask: eaTaskList) {
            Teacher tch = teacherService.getTeacherByRoleId(eaTask.getCreatorId());
            eaTaskDTOList.add(EATaskDTO.builder()
                    .eaTask(eaTask)
                    .creator(tch)
                    .build());
        }
        return new Result<>(eaTaskDTOList);
    }

    @ResponseBody
    @PostMapping("api/teacher/event/create")
    public Result<?> postEventCreation(@RequestBody EATaskCreationVO eaTaskCreationVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        // check project accessible
        if (!projService.isAccessible(role.getRoleId(), eaTaskCreationVO.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        EventArrangeTask eaTask = new EventArrangeTask();
        try {
            eaTaskCreationVO.copyToEATask(eaTask, role.getRoleId());
        } catch (ParseException e) {
            logger.info("postEventCreation fail: date format incorrect");
            return new Result<>(RespStatus.FAIL, "Date format must be yyyy-MM-dd HH:mm:ss");
        }

        boolean success = eaTaskService.createEATask(eaTask);

        if (success) {
            // send email to all students
            List<StudentProjDTO> stdList = projService.getStdProjList(eaTask.getProjId());
            List<String> mailList = stdList.stream().map(StudentProjDTO::getEmail).collect(Collectors.toList());
            mailService.sendMailToStudent(mailList, "[XProject] New event arrangement task has been released",
                    "New event arrangement task has been released\r\n" +
                            "This automatic notification message was sent by Xproject");
        }

        return createBoolResult(success, "Create successfully", "Create failed");
    }

    @ResponseBody
    @PostMapping("api/teacher/event/del")
    public Result<?> postEventDeletion(@RequestParam("eaTaskId") int eaTaskId) {
        boolean success = eaTaskService.deleteEATask(eaTaskId);
        return createBoolResult(success, "Delete successfully", "Delete failed");
    }

    /* EVENT INST */

    @ResponseBody
    @GetMapping("api/all/event/inst")
    public Result<?> getEventTaskList(@RequestParam("eaTaskId") int eaTaskId) {
        List<EventInst> eventInstList = eaTaskService.getEventInstList(eaTaskId);
        List<EventInstDTO> eventInstDTOList = new ArrayList<>();
        for (EventInst eventInst: eventInstList) {
            ProjectInst projInst = projInstService.getProjectInst(eventInst.getProjInstId());
            eventInstDTOList.add(EventInstDTO.builder()
                    .eventInst(eventInst)
                    .projInst(projInst)
                    .build());
        }
        return new Result<>(eventInstDTOList);
    }

    @ResponseBody
    @PostMapping("api/teacher/event/inst/create")
    public Result<?> postEventInstCreation(@RequestBody EventInstCreationVO eventInstCreationVO) {
//        String username = RoleUtils.getUsername();
//        Role role = roleService.getByUsername(username);

        SvResult<Integer> svResult = eaTaskService.createEventInsts(eventInstCreationVO);
        return new Result<>(svResult.getMsg(), svResult.getData());
    }

    @ResponseBody
    @PostMapping("api/teacher/event/inst/delete")
    public Result<?> postEventInstDeletion(@RequestBody EventInstListParamVO eventInstListParamVO) {
//        String username = RoleUtils.getUsername();
//        Role role = roleService.getByUsername(username);

        int[] eventInstIdList = eventInstListParamVO.getEventInstIdList();
        int successCnt = eaTaskService.deleteEventInsts(eventInstIdList);
        String message = "Delete " + successCnt + " event item successfully. Total "
                + eventInstListParamVO.getEventInstIdList().length;
        return new Result<>(message, successCnt);
    }

    @ResponseBody
    @PostMapping("api/student/event/inst/apply")
    public Result<?> postEventInstApply(@RequestParam("projId") int projId,
                                        @RequestParam("eventInstId") int eventInstId) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        // check project accessible
        if (!projService.isAccessible(role.getRoleId(), projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        ProjectInst projInst = projInstService.getPIByProjIdAndStdRoleId(projId, role.getRoleId());
        if (projInst == null) {
            return new Result<>(RespStatus.FAIL, "No Team yet");
        }

        // check team status
        if (!projInst.getStatus().equals(Confirm.name())) {
            return new Result<>(RespStatus.FAIL, "Your team has not been confirm yet");
        }

        SvResult<Boolean> svResult = eaTaskService.applyEventInst(eventInstId, projInst.getProjInstId());

        if (svResult.getData()) {
            // send email to all students
            List<StudentDTO> stdList = projInstService.getStudentDTOByProjInstId(projInst.getProjInstId());
            List<String> mailList = stdList.stream().map(StudentDTO::getEmail).collect(Collectors.toList());
            mailService.sendMailToStudent(mailList, "[XProject] Your team has taken a event item",
                    "Your team has taken a event item\r\n" +
                            "This automatic notification message was sent by Xproject");
        }

        return createBoolResult(svResult.getData(), "Apply successfully", svResult.getMsg());
    }

    @ResponseBody
    @PostMapping("api/student/event/inst/clear")
    public Result<?> postEventInstClearStd(@RequestParam("projId") int projId,
                                           @RequestParam("eventInstId") int eventInstId) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        // check project accessible
        if (!projService.isAccessible(role.getRoleId(), projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        ProjectInst projInst = projInstService.getPIByProjIdAndStdRoleId(projId, role.getRoleId());
        if (projInst == null) {
            return new Result<>(RespStatus.FAIL, "No Team yet");
        }

        SvResult<Boolean> svResult = eaTaskService.clearEventInstStd(eventInstId, projInst.getProjInstId());

        if (svResult.getData()) {
            // send email to all students
            List<StudentDTO> stdList = projInstService.getStudentDTOByProjInstId(projInst.getProjInstId());
            List<String> mailList = stdList.stream().map(StudentDTO::getEmail).collect(Collectors.toList());
            mailService.sendMailToStudent(mailList, "[XProject] Your team has given up a event item",
                    "Your team has given up a event item\r\n" +
                            "This automatic notification message was sent by Xproject");
        }

        return createBoolResult(svResult.getData(), "Clear successfully", svResult.getMsg());
    }

    @ResponseBody
    @PostMapping("api/teacher/event/inst/clear")
    public Result<?> postEventInstClearTch(@RequestBody EventInstListParamVO eventInstListParamVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        int[] eventInstIdList = eventInstListParamVO.getEventInstIdList();

        if (eventInstIdList.length == 0) {
            return new Result<>(RespStatus.FAIL, "Empty event inst id list");
        }

        // not to check

//        // check project accessible
//        if (!projService.isAccessible(role.getRoleId(), )) {
//            return new Result<>(RespStatus.FAIL, "Project is not accessible");
//        }

        int successCnt = eaTaskService.clearEventInstTch(eventInstListParamVO.getEventInstIdList());

        String message = "Clear " + successCnt + " event items. Total "
                + eventInstListParamVO.getEventInstIdList().length;
        return new Result<>(message, successCnt);
    }

    @ResponseBody
    @PostMapping("api/teacher/event/inst/manage")
    public Result<?> postEventInstAuto(@RequestBody EventInstManageParamVO eventInstManageParamVO) {
        // not to check
//        logger.info(String.format("getProjStdList -> %s", stdProjDTOList));
        int successCnt = eaTaskService.manageEventInsts(eventInstManageParamVO);
        String message = String.format("Manage %d event items. Total event item %d. Remain %d teams.",
                successCnt,
                eventInstManageParamVO.getEventInstIdList().length,
                eventInstManageParamVO.getProjInstIdList().length - successCnt);
        return new Result<>(message, successCnt);
    }


    @ResponseBody
    @GetMapping("api/teacher/event/team/no-arrange")
    public Result<?> getTeamsNoArrange(@RequestParam("projId") int projId,
                                       @RequestParam("eaTaskId") int eaTaskId) {
        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        List<ProjectInst> projInstList = projInstService.getProInstList(projId);
        List<SimpleTeamVO> simpleTeamVOList = new ArrayList<>();
        for (ProjectInst projectInst: projInstList) {
            // check arrange state
            boolean isArranged = eaTaskService.checkTeamArrangeState(eaTaskId, projectInst.getProjInstId());
            if (isArranged) {
                continue;
            }
            List<StudentDTO> studentList = projInstService.getStudentDTOByProjInstId(projectInst.getProjInstId());
            SimpleTeamVO simpleTeamVO = SimpleTeamVO.createFrom(projectInst, studentList.size());
            simpleTeamVOList.add(simpleTeamVO);
        }
//        logger.info("getTeamsNoArrange -> " + Arrays.toString(simpleTeamVOList.toArray()));
        return new Result<>(simpleTeamVOList);
    }
}
