package com.ooad.xproject.service;

import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.dto.GradeDTO;
import com.ooad.xproject.dto.RecordInstDTO;
import com.ooad.xproject.dto.StudentDTO;
import com.ooad.xproject.entity.ProjectInst;
import com.ooad.xproject.entity.RecordInst;
import com.ooad.xproject.entity.SubmissionInst;
import com.ooad.xproject.vo.ApplyReplyParamVO;
import com.ooad.xproject.vo.ApplyTeamParamVO;
import com.ooad.xproject.vo.ProjInstCreationVO;

import java.util.List;

public interface ProjInstService {

    ProjectInst getProjectInst(Integer piid);

    List<ProjectInst> getProInstList(Integer projId);

    List<StudentDTO> getStudentDTOByProjInstId(Integer projInstId);

    // select by record, return recordinst of the record
    List<RecordInst> getRecordInstList(Integer rid);

    // select by Project id and student's role id, return the student's RecordInsts under the project
    List<RecordInstDTO> getRecordInstList(Integer projId, Integer stdRoleId);

    // select by submission, return submissionInst of the submission
    List<SubmissionInst> getSubmissionInstList(Integer sbmid);

    // select by Project id and student id, return the student's submissionInsts under the submission
   List<SubmissionInst> getSubmissionInstList(Integer pid, Integer sid);

    SvResult<Boolean> deleteProjInst(int projInstId);

    SvResult<Boolean> confirmProjInst(int projInstId, boolean isForce, boolean isTeacher);

    ProjectInst getPIByProjIdAndStdRoleId(int projId, int stdRoleId);

    SvResult<Boolean> createProjInst(ProjInstCreationVO projInstCreationVO);

    SvResult<Boolean> createProjInstAndLink(int stdRoleId, ProjInstCreationVO projectCreationVO);

    boolean quitTeam(int stdRoleId, int projInstId);

    boolean updateProjInst(ProjectInst projectInst);

    SvResult<Boolean> applyTeam(Integer roleId, ApplyTeamParamVO atpVO);

    SvResult<Boolean> applyTeamReply(Integer roleId, ApplyReplyParamVO applyReplyParamVO);

    SvResult<Boolean> inviteTeamReply(Integer roleId, ApplyReplyParamVO applyReplyParamVO);

    List<GradeDTO> getTeamRecordInstList(int projInstId, int rcdId);

    String confirmBatchTch(int[] projInstIdList, boolean isForce);

    String cancelBatchTch(int[] projInstIdList);

    List<GradeDTO> getTeamRecordInstListStd(int stdRoleId, int rcdId);
}
