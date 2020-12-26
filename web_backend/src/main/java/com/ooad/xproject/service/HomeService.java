package com.ooad.xproject.service;

import com.ooad.xproject.entity.Announcement;
import com.ooad.xproject.entity.Project;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.School;

import java.util.List;

public interface HomeService {

    List<Project> getProjectList(int roleId);

    List<Announcement> getAnnounceList();

    School getSchool(int schId);

    int getSchIdByRole(Role role);

    List<Project> getProjectListBySch(Role role);

    boolean joinProject(int roleId, int projId, String groupMark);

    boolean quitProject(Integer roleId, int projId);
}
