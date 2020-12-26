package com.ooad.xproject.controller;

import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.School;
import com.ooad.xproject.mapper.SchoolMapper;
import com.ooad.xproject.service.*;
import com.ooad.xproject.utils.RoleUtils;
import com.ooad.xproject.vo.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SchoolController {

    private final RoleService roleService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final HomeService homeService;
    private final AnnService annService;
    private final SchoolMapper schoolMapper;        // not reasonable but any way
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    public SchoolController(RoleService testService, StudentService studentService, TeacherService teacherService, HomeService homeService, AnnService annService, SchoolMapper schoolMapper) {
        this.roleService = testService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.homeService = homeService;
        this.annService = annService;
        this.schoolMapper = schoolMapper;
    }

    @ResponseBody
    @GetMapping("api/visitor/role/school")
    public Result<?> getSchoolList() {
        List<School> schList = schoolMapper.selectAll();
        return new Result<>(schList);
    }

//    @ResponseBody
//    @PostMapping("api/teacher/project/ann/modify")
//    public Result<?> postModifyAnnouncement(@RequestBody Announcement announcement) {
//        boolean success = annService.updateAnn(announcement);
//        if (success) {
//            return new Result<>(true);
//        } else {
//            return new Result<>(RespStatus.FAIL, "Update failed", false);
//        }
//    }

    @ResponseBody
    @PostMapping("api/admin/school/add")
    public Result<?> postAddSchool(@RequestBody School school) {
        String username = RoleUtils.getUsername();
        Role role = roleService.getByUsername(username);

        school.setSchId(null);

        boolean success = schoolMapper.insertSelective(school) != 0;
        if (success) {
            return new Result<>(true);
        } else {
            return new Result<>(RespStatus.FAIL, "Add failed", false);
        }
    }

    @ResponseBody
    @GetMapping("api/admin/school/delete")
    public Result<?> getDeleteSchool(@RequestParam("schId") int schId) {
        boolean success = schoolMapper.deleteByPrimaryKey(schId) != 0;
        if (success) {
            return new Result<>(true);
        } else {
            return new Result<>(RespStatus.FAIL, "Delete failed", false);
        }
    }

    @ResponseBody
    @PostMapping("api/admin/school/edit")
    public Result<?> postEditSchool(@RequestBody School school){
        boolean success = roleService.updateSchool(school);
        return Result.createBoolResult(success, "Update school successfully", "Update school failed");
    }
}
