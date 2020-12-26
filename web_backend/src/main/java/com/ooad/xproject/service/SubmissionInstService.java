package com.ooad.xproject.service;

import com.ooad.xproject.entity.SubmissionInst;

import java.util.List;

public interface SubmissionInstService {
    int upsertSubmissionInst(SubmissionInst submissionInst);

    List<SubmissionInst> getSubmissionInstList(int sbmId, int projId);

}
