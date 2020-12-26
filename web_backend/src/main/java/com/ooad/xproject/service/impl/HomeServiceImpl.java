package com.ooad.xproject.service.impl;

import com.ooad.xproject.constant.RoleType;
import com.ooad.xproject.entity.*;
import com.ooad.xproject.mapper.ProjectMapper;
import com.ooad.xproject.mapper.SchoolMapper;
import com.ooad.xproject.mapper.StudentMapper;
import com.ooad.xproject.mapper.TeacherMapper;
import com.ooad.xproject.service.HomeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
    private final ProjectMapper projectMapper;
    private final SchoolMapper schoolMapper;
    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;

    public HomeServiceImpl(ProjectMapper projectMapper, SchoolMapper schoolMapper,
                           StudentMapper studentMapper, TeacherMapper teacherMapper) {
        this.projectMapper = projectMapper;
        this.schoolMapper = schoolMapper;
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public List<Project> getProjectList(int roleId) {
        return projectMapper.selectByRoleId(roleId);
    }


    //todo: get from all projects?
    @Override
    public ArrayList<Announcement> getAnnounceList() {
        return null;
    }

    @Override
    public School getSchool(int schId) {
        return schoolMapper.selectByPrimaryKey(schId);
    }

    @Override
    public int getSchIdByRole(Role role) {
        switch (RoleType.getRoleType(role.getRoleType())) {
            case Admin:
                throw new UnsupportedOperationException("Admin can't get school");
            case Teacher:
                Teacher tch = teacherMapper.selectByRoleId(role.getRoleId());
                return tch.getSchId();
            case Student:
                Student std = studentMapper.selectByRoleId(role.getRoleId());
                return std.getSchId();
            default:
                throw new UnsupportedOperationException("No such role type");
        }
    }

    @Override
    public List<Project> getProjectListBySch(Role role) {
        List<Project> projects;
        RoleType roleType = RoleType.getRoleType(role.getRoleType());
        switch (roleType) {
            case Admin:
                projects = projectMapper.selectAll();
                break;
            case Teacher:
                Teacher tch = teacherMapper.selectByRoleId(role.getRoleId());
                projects = projectMapper.selectBySchId(tch.getSchId());
                break;
            case Student:
                Student std = studentMapper.selectByRoleId(role.getRoleId());
                projects = projectMapper.selectBySchId(std.getSchId());
                break;
            default:
                projects = new ArrayList<>();
                break;
        }
        return projects;
    }

    @Override
    public boolean joinProject(int roleId, int projId, String groupMark) {
        try {
            int affectedRowCnt = projectMapper.joinProject(roleId, projId, groupMark);
            return affectedRowCnt == 1;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean quitProject(Integer roleId, int projId) {
        int affectedRowCnt = projectMapper.quitProject(roleId, projId);
        return affectedRowCnt != 0;
    }
}
