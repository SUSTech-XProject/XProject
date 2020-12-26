package com.ooad.xproject.service;

import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.entity.Submission;
import com.ooad.xproject.entity.SubmissionInst;

import java.util.List;

public interface SbmService {

    List<Submission> getSbmListByProjId(Integer projId);

    boolean createSubmission(Submission sbm);

    boolean deleteSubmission(int sbmId);

    boolean modifySubmission(Submission sbm);

    SvResult<SubmissionInst> getSbmInstByStdRoleIdAndSbmId(int roleId, int sbmId);

    Submission getSbmBySbmId(int sbmId);
}
