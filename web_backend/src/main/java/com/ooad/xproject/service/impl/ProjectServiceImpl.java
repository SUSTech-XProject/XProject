package com.ooad.xproject.service.impl;

import com.ooad.xproject.bo.RecordUnitBO;
import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.bo.TeamBO;
import com.ooad.xproject.bo.forming.FormingBO;
import com.ooad.xproject.bo.forming.FormingResultBO;
import com.ooad.xproject.constant.RoleType;
import com.ooad.xproject.dto.StudentProjDTO;
import com.ooad.xproject.entity.*;
import com.ooad.xproject.mapper.*;
import com.ooad.xproject.service.MailService;
import com.ooad.xproject.service.ProjectService;
import com.ooad.xproject.utils.RoleUtils;
import org.apache.commons.math3.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final AnnouncementMapper announcementMapper;
    private final EventArrangeTaskMapper eventArrangeTaskMapper;
    private final SubmissionMapper submissionMapper;
    private final RecordMapper recordMapper;
    private final ProjectMapper projectMapper;
    private final ProjectInstMapper projectInstMapper;
    private final TeamFormTaskMapper teamFormTaskMapper;
    private final StudentMapper studentMapper;
    private final RoleMapper roleMapper;
    private final MailService mailService;

    public ProjectServiceImpl(AnnouncementMapper announcementMapper, EventArrangeTaskMapper eventArrangeTaskMapper, SubmissionMapper submissionMapper, RecordMapper recordMapper, ProjectMapper projectMapper, ProjectInstMapper projectInstMapper, TeamFormTaskMapper teamFormTaskMapper, StudentMapper studentMapper, RoleMapper roleMapper, MailService mailService) {
        this.announcementMapper = announcementMapper;
        this.eventArrangeTaskMapper = eventArrangeTaskMapper;
        this.submissionMapper = submissionMapper;
        this.recordMapper = recordMapper;
        this.projectMapper = projectMapper;
        this.projectInstMapper = projectInstMapper;
        this.teamFormTaskMapper = teamFormTaskMapper;
        this.studentMapper = studentMapper;
        this.roleMapper = roleMapper;
        this.mailService = mailService;
    }

    @Override
    public Project getProject(Integer pid) {
        return projectMapper.selectByPrimaryKey(pid);
    }

    @Override
    public List<ProjectInst> getProjectInstList(Integer pid) {
        return projectInstMapper.selectByProjId(pid);
    }

    @Override
    public List<Announcement> getAnnounceList(Integer pid) {
        return announcementMapper.selectByProjId(pid);
    }

    @Override
    public List<EventArrangeTask> getEventList(Integer pid) {
        return eventArrangeTaskMapper.selectByProjId(pid);
    }

    @Override
    public List<Submission> getSubmissionList(Integer pid) {
        return submissionMapper.selectByProjId(pid);
    }

    @Override
    public List<Record> getRecordList(Integer pid) {
        return recordMapper.selectByProjId(pid);
    }

    @Override
    public TeamFormTask getTeamFormTask(Integer pid) {
        return teamFormTaskMapper.selectByPrimaryKey(pid);
    }

    @Override
    public boolean updateProject(Project project) {
        int affectedRowCnt = projectMapper.updateByPrimaryKeySelective(project);
        return affectedRowCnt == 1;
    }

    @Override
    public List<RecordUnitBO> getRecordUnitList(Integer pid) {
        return projectMapper.selectRecordUnitByProjId(pid);
    }

    @Override
    public List<StudentProjDTO> getStdProjList(int projId) {
        return studentMapper.selectProjStudents(projId);
    }

    @Override
    public List<TeamBO> getTeamList(int projId) {
        return projectInstMapper.selectTeamByProjId(projId);
    }

    @Override
    public SvResult<FormingResultBO> autoForming(FormingBO formingBO) {
        FormingResultBO res = formingBO.executeForming();
        for (Pair<Integer, Integer> pair : res.getMatchList()) {
            int projInstId = pair.getFirst();
            int stdRoleId = pair.getSecond();
            try {
                // check team status
                ProjectInst projInst = projectInstMapper.selectByPrimaryKey(projInstId);
                int projId = projInst.getProjId();
                ProjectInst projInstApplicant = projectInstMapper.selectPIByProjIdAndStdRoleId(projId, stdRoleId);
                if (projInstApplicant != null) {
                    res.reduceSuccess(1);
                    System.out.println("Fail in autoForming (already has a team)" + pair.toString());
                    continue;
                }

                int affectedRowCnt = projectInstMapper.insertProjInstStdRT(pair.getFirst(), pair.getSecond(), null);
                if (affectedRowCnt == 0) {
                    res.reduceSuccess(1);
                    System.out.println("Fail in autoForming " + pair.toString());
                } else {
                    Student std = studentMapper.selectByRoleId(pair.getSecond());
                    mailService.sendSimpleMail(std.getEmail(), "[XProject] Your have been add to one team",
                            "Your have been add to one team by teacher\r\n" +
                                    "This automatic notification message was sent by Xproject");
                }
            } catch (Exception e) {
                e.printStackTrace();
                res.reduceSuccess(1);
            }
        }

        System.out.println(Arrays.toString(res.getMatchList().toArray()));
        System.out.println(res.getMessage());

        return new SvResult<>("", res);
    }

    @Override
    public boolean createProject(Project project) {
        int affectedRowCnt = projectMapper.insertSelective(project);
        return affectedRowCnt == 1;
    }

    @Override
    public boolean isAccessible(int projId) {
        String username = RoleUtils.getUsername();
        Role role = roleMapper.selectByUsername(username);
        return isAccessible(role.getRoleId(), projId);
    }

    @Override
    public boolean isAccessible(int roleId, int projId) {
        return projectMapper.selectExistByRoleIdAndProjId(roleId, projId);
    }

    @Override
    public boolean canJoin(Role role, int projId) {
        Project project = projectMapper.selectByPrimaryKey(projId);
        return canJoin(role, project);
    }

    @Override
    public boolean canJoin(Role role, Project project) {
        if (RoleType.getRoleType(role.getRoleType()).equals(RoleType.Teacher)) {
            return project.getTchJoin();
        } else {
            return project.getStdJoin();
        }
    }

    @Override
    public boolean deleteProject(int projId) {
        return projectMapper.deleteByPrimaryKey(projId) == 1;
    }
}
