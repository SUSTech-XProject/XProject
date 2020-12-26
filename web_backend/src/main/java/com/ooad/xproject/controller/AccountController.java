package com.ooad.xproject.controller;

import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.constant.RoleType;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.School;
import com.ooad.xproject.entity.Student;
import com.ooad.xproject.entity.Teacher;
import com.ooad.xproject.service.HomeService;
import com.ooad.xproject.service.RoleService;
import com.ooad.xproject.service.StudentService;
import com.ooad.xproject.service.TeacherService;
import com.ooad.xproject.utils.RoleUtils;
import com.ooad.xproject.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final RoleService roleService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final HomeService homeService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    public AccountController(RoleService testService, StudentService studentService, TeacherService teacherService, HomeService homeService) {
        this.roleService = testService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.homeService = homeService;
    }

    @ResponseBody
    @GetMapping("api/all/account/info")
    public Result<?> getAccountInfo() {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        if (RoleType.Student.match(role.getRoleType())) {
            Student student = studentService.getStudentByRoleId(role.getRoleId());
            School school = homeService.getSchool(student.getSchId());
            AccountInfoStdVO accountInfoStdVO = AccountInfoStdVO.createFrom(role, student, school);
            return new Result<>(accountInfoStdVO);
        } else if (RoleType.Teacher.match(role.getRoleType())) {
            Teacher teacher = teacherService.getTeacherByRoleId(role.getRoleId());
            School school = homeService.getSchool(teacher.getSchId());
            AccountInfoTchVO accountInfoTchVO = AccountInfoTchVO.createFrom(role, teacher, school);
            return new Result<>(accountInfoTchVO);
        } else {
            return new Result<>(RespStatus.FAIL, "Admin can't call this api");
        }
    }

    @ResponseBody
    @GetMapping("api/all/team/stu-info")
    public Result<?> getStuInfo(@RequestParam("roleId") int roleId) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        int schId;
        if (RoleType.Student.match(role.getRoleType())) {
            Student student = studentService.getStudentByRoleId(role.getRoleId());
            schId = student.getSchId();
        } else {
            Teacher teacher = teacherService.getTeacherByRoleId(role.getRoleId());
            schId = teacher.getSchId();
        }

        Role targetRole = roleService.getByRoleId(roleId);

        if (!RoleType.Student.match(targetRole.getRoleType())) {
            return new Result<>(RespStatus.FAIL, "Getting non-student info is forbidden");
        }
        Student target = studentService.getStudentByRoleId(roleId);

        if (target == null) {
            // not the same school
            return new Result<>(RespStatus.FAIL, "No such student");
        }

        if (schId != target.getSchId()) {
            // not the same school
            return new Result<>(RespStatus.FAIL, "School not matched");
        }

        School school = homeService.getSchool(schId);
        AccountInfoStdVO accountInfoStdVO = AccountInfoStdVO.createFrom(targetRole, target, school);

        return new Result<>(accountInfoStdVO);
    }

    @ResponseBody
    @PostMapping("api/student/self-intro")
    public Result<?> postUpdateAccountInfo(@RequestBody AcInfoStdUpdateVO acInfoStdUpdateVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        boolean success = studentService.updateAcInfo(role, acInfoStdUpdateVO);
        if (success) {
            return new Result<>(RespStatus.SUCCESS);
        } else {
            return new Result<>(RespStatus.FAIL);
        }
    }

    @ResponseBody
    @PostMapping("api/teacher/self-intro")
    public Result<?> postTchPersonalInfo(@RequestBody AcInfoTchUpdateVO acInfoTchUpdateVO) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        boolean success = teacherService.updateAcInfo(role, acInfoTchUpdateVO);
        if (success) {
            return new Result<>(RespStatus.SUCCESS);
        } else {
            return new Result<>(RespStatus.FAIL);
        }
    }

    @ResponseBody
    @PostMapping("api/student/review/teammates")
    public Result<?> postReviewToTeammates(@RequestBody StdCommentsVO stdCommentsVOS) {
        int successCnt = 0;
        int[] tarRoleIds = stdCommentsVOS.getTarRoleIds();
        String[] comments = stdCommentsVOS.getComments();
        for (int i = 0; i < tarRoleIds.length; ++i) {
            if(studentService.appendStdPayload(tarRoleIds[i], comments[i])){
                ++successCnt;
            }
        }
        if (successCnt > 0) {
            return new Result<>(RespStatus.SUCCESS, "comment success");
        } else {
            return new Result<>(RespStatus.FAIL, "comment fail");
        }
    }
}
