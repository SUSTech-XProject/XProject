package com.ooad.xproject.controller;

import com.alibaba.fastjson.JSON;
import com.ooad.xproject.bo.CommentBO;
import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.constant.RoleType;
import com.ooad.xproject.dto.StudentDTO;
import com.ooad.xproject.entity.Project;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.Student;
import com.ooad.xproject.entity.Teacher;
import com.ooad.xproject.service.*;
import com.ooad.xproject.utils.RoleUtils;
import com.ooad.xproject.vo.QuitProjParamVO;
import com.ooad.xproject.vo.Result;
import com.ooad.xproject.vo.StdUpdateVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomePageController {

    private final RoleService roleService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final HomeService homeService;
    private final ProjectService projService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    public HomePageController(RoleService roleService, TeacherService teacherService, StudentService studentService, HomeService homeService, ProjectService projService) {
        this.roleService = roleService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.homeService = homeService;
        this.projService = projService;
    }

    @ResponseBody
    @GetMapping("api/all/home-page/info")
    public Result<?> getHomePageInfo() {
        logger.info("getHomePageInfo");
        return new Result<>(RespStatus.NOT_IMPLEMENTED, "Use account-info instead");
    }

    @ResponseBody
    @GetMapping("api/all/project/list")
    public Result<?> getProjectList() {
        logger.info("getProjectList");
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();

        Role role = roleService.getByUsername(username);
        List<Project> projList = homeService.getProjectList(role.getRoleId());
        return new Result<>(projList);
    }

    @ResponseBody
    @PostMapping("api/all/project/join")
    public Result<?> postJoinProject(@RequestParam("projId") int projId,
                                     @RequestParam(value = "groupMark", required = false, defaultValue = "Default") String groupMark) {
        logger.info("joinProject");
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();

        Role role = roleService.getByUsername(username);

        // check school
        Project project = projService.getProject(projId);
        int schId = homeService.getSchIdByRole(role);
        if (project.getSchId() != schId) {
            return new Result<>(RespStatus.FAIL, "School not match");
        }

        // check project settings
        if (!projService.canJoin(role, project)) {
            return new Result<>(RespStatus.FAIL, "You forbidden to join the project");
        }

        boolean success = homeService.joinProject(role.getRoleId(), projId, groupMark);

        if (success) {
            return new Result<>(true);
        } else {
            return new Result<>(RespStatus.FAIL, "Join project failed", false);
        }
    }

    @ResponseBody
    @PostMapping("api/all/proj/quit")
    public Result<?> postProjQuit(@RequestParam("projId") int projId) {
        logger.info("postProjQuit");
        Role role = roleService.getByUsername(RoleUtils.getUsername());

        boolean success = homeService.quitProject(role.getRoleId(), projId);

        if (success) {
            return new Result<>("Quit project successfully", true);
        } else {
            return new Result<>(RespStatus.FAIL, "Quit project failed", false);
        }
    }

    @ResponseBody
    @PostMapping("api/teacher/project/delete/std")
    public Result<?> postDeleteStdIntoProj(@RequestBody QuitProjParamVO quitProjParamVO) {
        logger.info("postDeleteStdIntoProj");
        Role role = roleService.getByUsername(RoleUtils.getUsername());

        // check project accessible
        if (!projService.isAccessible(quitProjParamVO.getProjId())) {
            return new Result<>(RespStatus.FAIL, "Project is not accessible");
        }

        int successCnt = 0;
        for (int roleId : quitProjParamVO.getRoleIdList()) {
            boolean success = homeService.quitProject(roleId, quitProjParamVO.getProjId());
            successCnt += success ? 1 : 0;
        }

        return new Result<>("Delete " + successCnt + " student from project successfully", true);
    }


    @ResponseBody
    @GetMapping("api/all/project/by-sch")
    public Result<?> getProjectListBySch() {
        logger.info("getProjectListBySch");
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();

        Role role = roleService.getByUsername(username);
        List<Project> projList = homeService.getProjectListBySch(role);
        return new Result<>(projList);
    }

    @ResponseBody
    @GetMapping("api/teacher/students")
    public Result<?> postSelectStudents() {
        logger.info("postSelectStudents");
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        Role role = roleService.getByUsername(username);

        Teacher teacher = teacherService.getTeacherByRoleId(role.getRoleId());

        List<StudentDTO> stdDTOList = studentService.getAllStudentList(teacher);

        return new Result<>(stdDTOList);
    }

    @ResponseBody
    @PostMapping("api/teacher/student/info/update")
    public Result<?> postUpdateStudentInfo(@RequestBody StdUpdateVO stdUpdateVO) {
        logger.info("postUpdateStudentInfo");

        Role tchRole = roleService.getByUsername(RoleUtils.getUsername());
        int schId = homeService.getSchIdByRole(tchRole);

        Role stdRole = roleService.getByRoleId(stdUpdateVO.getRoleId());
        int schIdStd = homeService.getSchIdByRole(stdRole);
        if (schId != schIdStd) {
            return new Result<>(RespStatus.FAIL, "School not matched");
        }

        boolean success = studentService.updateStdInfo(stdUpdateVO);
        return Result.createBoolResult(success, "Update successfully", "Update failed");
    }

    @ResponseBody
    @GetMapping("api/all/comments")
    public Result<?> getComments() {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);
        return getCommentsByRoleId(role.getRoleId());
    }

    @ResponseBody
    @GetMapping("api/all/comments/roleId")
    public Result<?> getCommentsByRoleId(@RequestParam("roleId") int roleId) {
        if (roleService.getByRoleId(roleId).getRoleType().matches("Student")) {
            Student student = studentService.getStudentByRoleId(roleId);
            return new Result<>(student.getPayload());
        } else {
            return new Result<>("{}");
        }
    }
}
