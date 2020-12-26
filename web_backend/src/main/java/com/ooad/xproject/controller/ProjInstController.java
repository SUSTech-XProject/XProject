package com.ooad.xproject.controller;

import com.ooad.xproject.bo.MessageFactory;
import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.constant.RoleType;
import com.ooad.xproject.dto.StudentDTO;
import com.ooad.xproject.entity.Message;
import com.ooad.xproject.entity.ProjectInst;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.Student;
import com.ooad.xproject.service.*;
import com.ooad.xproject.utils.RoleUtils;
import com.ooad.xproject.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ooad.xproject.constant.ProjInstStatus.Confirm;

@RestController
public class ProjInstController {

    private final RoleService roleService;
    private final ProjectService projService;
    private final ProjInstService projInstService;
    private final MessageService messageService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private final StudentService studentService;
    private final MailService mailService;

    public ProjInstController(RoleService roleService, ProjectService projService, ProjInstService projInstService, MessageService messageService, StudentService studentService, MailService mailService) {
        this.roleService = roleService;
        this.projService = projService;
        this.projInstService = projInstService;
        this.messageService = messageService;
        this.studentService = studentService;
        this.mailService = mailService;
    }

    @ResponseBody
    @GetMapping("api/all/team/list")
    public Result<?> getTeamInfoList(@RequestParam(value="projId") int projId) {
        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        List<ProjectInst> projInstList = projInstService.getProInstList(projId);
        List<SimpleTeamVO> simpleTeamVOList = new ArrayList<>();
        for (ProjectInst projectInst: projInstList) {
            List<StudentDTO> studentList = projInstService.getStudentDTOByProjInstId(projectInst.getProjInstId());
            SimpleTeamVO simpleTeamVO = SimpleTeamVO.createFrom(projectInst, studentList.size());
            simpleTeamVOList.add(simpleTeamVO);
        }
//        logger.info("getTeamInfoList -> " + Arrays.toString(simpleTeamVOList.toArray()));
        return new Result<>(simpleTeamVOList);
    }

    @ResponseBody
    @GetMapping("api/all/team/info/detail")
    public Result<?> getTeamDetail(@RequestParam(value="teamId") int projInstId) {
        ProjectInst projectInst = projInstService.getProjectInst(projInstId);

        if (projectInst == null) {
            return new Result<>(RespStatus.FAIL, "No such projInst");
        }

        // check project accessible
        if (!projService.isAccessible(projectInst.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        List<StudentDTO> studentList = projInstService.getStudentDTOByProjInstId(projInstId);
        TeamVO teamVO = TeamVO.createFrom(projectInst, studentList);
//        logger.info(String.format("getTeamDetail -> %s", teamVO));
        return new Result<>(teamVO);
    }

    @ResponseBody
    @PostMapping("api/student/team/change/info")
    public Result<?> postEditedTeamInfo(@RequestBody ProjInstUpdateVO projInstUpdateVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        // check project accessible
        if (!projService.isAccessible(role.getRoleId(), projInstUpdateVO.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        ProjectInst projectInst = projInstService.getPIByProjIdAndStdRoleId(projInstUpdateVO.getProjId(), role.getRoleId());
        if (projectInst == null) {
            return new Result<>(RespStatus.FAIL, "No team");
        }

        if (projectInst.getStatus().equals(Confirm.name())) {
            return new Result<>(RespStatus.FAIL, "Team has already confirmed");
        }

        projInstUpdateVO.copyToProjInst(projectInst);
        boolean success = projInstService.updateProjInst(projectInst);
        return Result.createBoolResult(success, "Update successfully", "Update failed");
    }

    @ResponseBody
    @PostMapping("api/teacher/team/change")
    public Result<?> postEditedTeamTchInfo(@RequestBody ProjInstUpdateTchVO projInstUpdateTchVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        ProjectInst projectInst = projInstService.getProjectInst(projInstUpdateTchVO.getProjInstId());

        if (projectInst == null) {
            return new Result<>(RespStatus.FAIL, "No team");
        }

        // check project accessible
        if (!projService.isAccessible(role.getRoleId(), projectInst.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        projInstUpdateTchVO.copyToProjInst(projectInst);
        boolean success = projInstService.updateProjInst(projectInst);
        return Result.createBoolResult(success, "Update successfully", "Update failed");
    }

    @ResponseBody
    @GetMapping("api/student/team/detail")
    public Result<?> getMyTeamDetail(@RequestParam(value="projId") int projId) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        ProjectInst projInst = projInstService.getPIByProjIdAndStdRoleId(projId, role.getRoleId());

        if (projInst == null) {
            return new Result<>(RespStatus.FAIL, "No team");
        }

        List<StudentDTO> studentList = projInstService.getStudentDTOByProjInstId(projInst.getProjInstId());
        TeamVO teamVO = TeamVO.createFrom(projInst, studentList);

//        logger.info(String.format("getTeamDetail -> %s", projInst));
        return new Result<>(teamVO);
    }

    @ResponseBody
    @PostMapping("api/student/team/apply")
    public Result<?> postTeamApply(@RequestBody ApplyTeamParamVO applyTeamParamVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        ProjectInst projInst = projInstService.getProjectInst(applyTeamParamVO.getProjInstId());

        // check project accessible
        if (!projService.isAccessible(role.getRoleId(), projInst.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        if (projInst.getStatus().equals(Confirm.name())) {
            return new Result<>(RespStatus.FAIL, "This team has already been confirmed.");
        }

        SvResult<Boolean> svResult = projInstService.applyTeam(role.getRoleId(), applyTeamParamVO);
        return new Result<>(svResult.getMsg(), svResult.getData());
    }

    @ResponseBody
    @PostMapping("api/student/team/reply/application")
    public Result<?> postReplyApplication(@RequestBody ApplyReplyParamVO applyReplyParamVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        Message message = messageService.getMessageByMsgId(applyReplyParamVO.getMsgId());

        // check project accessible
        if (!projService.isAccessible(message.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        // not to check projInst

        // check projInst status in service

        SvResult<Boolean> svResult = projInstService.applyTeamReply(role.getRoleId(), applyReplyParamVO);
        return new Result<>(svResult.getMsg(), svResult.getData());
    }

    // reuse paramVO
    @ResponseBody
    @PostMapping("api/student/team/reply/invite")
    public Result<?> postReplyInvite(@RequestBody ApplyReplyParamVO applyReplyParamVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        Message message = messageService.getMessageByMsgId(applyReplyParamVO.getMsgId());

        // check project accessible
        if (!projService.isAccessible(message.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        // not to check projInst

        // check projInst status in service

        SvResult<Boolean> svResult = projInstService.inviteTeamReply(role.getRoleId(), applyReplyParamVO);
        return new Result<>(svResult.getMsg(), svResult.getData());
    }

    @ResponseBody
    @PostMapping("api/all/team/creation")
    public Result<Integer> postTeamCreation(@RequestBody ProjInstCreationVO projInstCreationVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        // check project accessible
        if (!projService.isAccessible(projInstCreationVO.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        if (RoleType.Teacher.match(role.getRoleType())) {

            int successCnt = 0;
            boolean useIncrNum = projInstCreationVO.isUseIncrNum();
            int generateNum = projInstCreationVO.getGenerateNum();
            String baseTeamName = projInstCreationVO.getTeamName();

            for (int i = 0; i < generateNum; i++) {
                if (useIncrNum) {
                    projInstCreationVO.setTeamName(baseTeamName + ' ' + i);
                }
                SvResult<Boolean> svResult = projInstService.createProjInst(projInstCreationVO);
                if (svResult.getData()) {
                    successCnt ++;
                } else {
                    // fail to create
                    logger.info("postTeamCreation: create failed when i=" + i + ". " + svResult.getMsg());
                }
            }
            logger.info("postTeamCreation: creation process done for teacher. successCnt=" + successCnt);
            RespStatus status = (successCnt == 0) ? RespStatus.FAIL : RespStatus.SUCCESS;
            String msg = (successCnt == 0) ? "Create teams fail" : "Create teams done";
            return new Result<>(status, msg, successCnt);

        } else if (RoleType.Student.match(role.getRoleType())) {
            ProjectInst projectInst = projInstService
                    .getPIByProjIdAndStdRoleId(projInstCreationVO.getProjId(), role.getRoleId());
            if (projectInst != null) {
                return new Result<>(RespStatus.FAIL, "In team", 0);
            }
            SvResult<Boolean> svResult = projInstService.createProjInstAndLink(role.getRoleId(), projInstCreationVO);
            if (svResult.getData()) {
                logger.info("postTeamCreation: create success for student. " + svResult.getMsg());
                Student std = studentService.getStudentByRoleId(role.getRoleId());
                // send team create email
                mailService.sendSimpleMail(std.getEmail(), "[XProject] You have create a new team",
                        "You have create a new team\r\n" +
                                "This automatic notification message was sent by Xproject");
                return new Result<>(RespStatus.SUCCESS, svResult.getMsg(), 1);
            } else {
                logger.info("postTeamCreation: create fail for student. " + svResult.getMsg());
                return new Result<>(RespStatus.FAIL, svResult.getMsg(), 0);
            }

        } else {
            return new Result<>(RespStatus.NOT_IMPLEMENTED, "Type Error");
        }
    }

    @ResponseBody
    @PostMapping("api/teacher/team/deletion")
    public Result<Integer> postTeamDeletion(@RequestBody ProjInstIdListVO projInstIdListVO) {
        // not to check access
        List<Integer> successList = new ArrayList<>();
        for (int projInstId : projInstIdListVO.getProjInstIdList()) {
            SvResult<Boolean> svResult = projInstService.deleteProjInst(projInstId);
            if (svResult.getData()) {
                // true
                successList.add(projInstId);
            } else {
                // false
                logger.info(String.format("postTeamDeletion: delete fail. " +
                        "projInstId=%d, msg=%s", projInstId, svResult.getMsg()));
            }
        }
        int successCnt = successList.size();
        RespStatus status = (successCnt == 0) ? RespStatus.FAIL : RespStatus.SUCCESS;
        String msg = (successCnt == 0) ? "Delete all teams fail" : "Delete teams done";
        return new Result<>(status, msg, successCnt);
    }

    @ResponseBody
    @PostMapping("api/all/team/confirm")
    public Result<?> postTeamConfirmation(@RequestBody TeamConfirmParamVO teamConfirmParamVO) {
        Role role = roleService.getByUsername(RoleUtils.getUsername());

        // not to check access

        if (RoleType.Student.match(role.getRoleType())) {
            int projInstId = teamConfirmParamVO.getProjInstIdList()[0];
            SvResult<Boolean> svResult = projInstService.confirmProjInst(projInstId, false, false);
            if (svResult.getData()) {
                // send email to all members
                List<StudentDTO> stdList = projInstService.getStudentDTOByProjInstId(projInstId);
                List<String> mailList = stdList.stream().map(StudentDTO::getEmail).collect(Collectors.toList());
                mailService.sendMailToStudent(mailList, "[XProject] Your team has been confirmed",
                        "Your team has been confirmed\r\n" +
                                "This automatic notification message was sent by Xproject");
            }
            return new Result<>(svResult.getMsg(), svResult.getData() ? 1 : 0);
        } else {
            String message = projInstService.confirmBatchTch(teamConfirmParamVO.getProjInstIdList(),
                    teamConfirmParamVO.isForce());
            return new Result<>(message);
        }
    }

    // reuse
    @ResponseBody
    @PostMapping("api/teacher/team/cancel")
    public Result<?> postTeamCancel(@RequestBody TeamConfirmParamVO teamConfirmParamVO) {
        Role role = roleService.getByUsername(RoleUtils.getUsername());

        // not to check access

        if (RoleType.Student.match(role.getRoleType())) {
            int projInstId = teamConfirmParamVO.getProjInstIdList()[0];
            SvResult<Boolean> svResult = projInstService.confirmProjInst(projInstId, false, false);
            if (svResult.getData()) {
                // send email to all members
                List<StudentDTO> stdList = projInstService.getStudentDTOByProjInstId(projInstId);
                List<String> mailList = stdList.stream().map(StudentDTO::getEmail).collect(Collectors.toList());
                mailService.sendMailToStudent(mailList, "[XProject] Your team status has been canceled by teacher",
                        "Your team status has been canceled by teacher\r\n" +
                                "This automatic notification message was sent by Xproject");
            }
            return new Result<>(svResult.getMsg(), svResult.getData() ? 1 : 0);
        } else {
            String message = projInstService.cancelBatchTch(teamConfirmParamVO.getProjInstIdList());
            return new Result<>(message);
        }
    }


    @ResponseBody
    @PostMapping("api/student/team/quit")
    public Result<?> postQuitTeam(@RequestParam(value="projId") int projId) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);
        ProjectInst projectInst = projInstService.getPIByProjIdAndStdRoleId(projId, role.getRoleId());

        if (projectInst == null) {
            return new Result<>(RespStatus.FAIL, "No team");
        }

        boolean success = projInstService.quitTeam(role.getRoleId(), projectInst.getProjInstId());
        if (success) {
            Student std = studentService.getStudentByRoleId(role.getRoleId());
            mailService.sendSimpleMail(std.getEmail(), "[XProject] You are quit from the team",
                    "You are quit from the team\r\n" +
                            "This automatic notification message was sent by Xproject");

            Message msg = MessageFactory.createQuitMessage(projectInst, role.getRoleId());
            messageService.createMessage(msg);

            return new Result<>("Quit successfully");
        } else {
            return new Result<>(RespStatus.FAIL, "Quit failed");
        }
    }

    @ResponseBody
    @GetMapping("api/all/teammates/by/id")
    public Result<?> getTeammatesByRoleId(@RequestParam(value="projId") int projId,
                                          @RequestParam("roleId") int roleId) {

        ProjectInst projectInst = projInstService.getPIByProjIdAndStdRoleId(projId, roleId);
        if (projectInst == null) {
            return new Result<>(RespStatus.FAIL, "No team");
        }

        // check project accessible
        if (!projService.isAccessible(projId)) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        List<StudentDTO> stdDTOList = projInstService.getStudentDTOByProjInstId(projectInst.getProjInstId());
        return new Result<>(stdDTOList);
    }

    // reuse QuitProjParamVO
    @ResponseBody
    @PostMapping("api/teacher/clear/std/team")
    public Result<?> postClearStdTeam(@RequestBody QuitProjParamVO quitProjParamVO) {
        logger.info("postClearStdTeam");
        Role role = roleService.getByUsername(RoleUtils.getUsername());

        // check project accessible
        if (!projService.isAccessible(role.getRoleId(), quitProjParamVO.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        int successCnt = 0;
        int projId = quitProjParamVO.getProjId();
        for (int roleId : quitProjParamVO.getRoleIdList()) {
            ProjectInst projInst = projInstService.getPIByProjIdAndStdRoleId(projId, roleId);
            if (projInst == null) {
                continue;
            }
            boolean success = projInstService.quitTeam(roleId, projInst.getProjInstId());
            if (success) {
                successCnt += 1;
                Student std = studentService.getStudentByRoleId(roleId);
                mailService.sendSimpleMail(std.getEmail(), "[XProject] You are cleared from the team by teacher",
                        "You are cleared from the team by teacher\r\n" +
                                "This automatic notification message was sent by Xproject");
            }
        }

        return new Result<>("Delete " + successCnt + " student from team successfully", true);
    }

}
