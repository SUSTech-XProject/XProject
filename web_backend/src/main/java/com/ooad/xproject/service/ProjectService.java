package com.ooad.xproject.service;

import com.ooad.xproject.bo.RecordUnitBO;
import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.bo.TeamBO;
import com.ooad.xproject.bo.forming.FormingBO;
import com.ooad.xproject.bo.forming.FormingResultBO;
import com.ooad.xproject.dto.StudentProjDTO;
import com.ooad.xproject.entity.*;

import java.util.List;

public interface ProjectService {
    Project getProject(Integer pid);

    // select by project id, return the list of ProjectInsts
    List<ProjectInst> getProjectInstList(Integer pid);

    // select by project id, return the list of Announcements
    List<Announcement> getAnnounceList(Integer pid);

    // select by project id, return the list of Events
    List<EventArrangeTask> getEventList(Integer pid);

    // select by project id, return the list of Submissions
    List<Submission> getSubmissionList(Integer pid);

    // select by project id, return the list of Records
    List<Record> getRecordList(Integer pid);

    // select by project id, return the Team form task of project
    TeamFormTask getTeamFormTask(Integer pid);

    boolean updateProject(Project project);

    List<RecordUnitBO> getRecordUnitList(Integer pid);

    List<StudentProjDTO> getStdProjList(int projId);

    List<TeamBO> getTeamList(int projId);

    SvResult<FormingResultBO> autoForming(FormingBO formingBO);

    boolean createProject(Project project);

    boolean isAccessible(int projId);

    boolean isAccessible(int roleId, int projId);

    boolean canJoin(Role role, int projId);

    boolean canJoin(Role role, Project project);

    boolean deleteProject(int projId);
}
