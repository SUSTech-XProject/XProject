package com.ooad.xproject.service.impl;

import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.entity.ProjectInst;
import com.ooad.xproject.entity.Submission;
import com.ooad.xproject.entity.SubmissionInst;
import com.ooad.xproject.mapper.ProjectInstMapper;
import com.ooad.xproject.mapper.SubmissionInstMapper;
import com.ooad.xproject.mapper.SubmissionMapper;
import com.ooad.xproject.service.SbmService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SbmServiceImpl implements SbmService {

    private final SubmissionMapper submissionMapper;
    private final SubmissionInstMapper sbmInstMapper;
    private final ProjectInstMapper projInstMapper;

    public SbmServiceImpl(SubmissionMapper submissionMapper, SubmissionInstMapper sbmInstMapper, ProjectInstMapper projInstMapper) {
        this.submissionMapper = submissionMapper;
        this.sbmInstMapper = sbmInstMapper;
        this.projInstMapper = projInstMapper;
    }

    @Override
    public List<Submission> getSbmListByProjId(Integer projId) {
        return submissionMapper.selectByProjId(projId);
    }

    @Override
    public boolean createSubmission(Submission sbm) {
        sbm.setSbmId(null);
        int affectedRowCnt = submissionMapper.insertSelective(sbm);
        return affectedRowCnt == 1;
    }

    @Override
    public boolean deleteSubmission(int sbmId) {
        int affectedRowCnt = submissionMapper.deleteByPrimaryKey(sbmId);
        return affectedRowCnt == 1;
    }

    @Override
    public boolean modifySubmission(Submission sbm) {
        int affectedRowCnt = submissionMapper.updateByPrimaryKey(sbm);
        return affectedRowCnt == 1;
    }

    @Override
    public SvResult<SubmissionInst> getSbmInstByStdRoleIdAndSbmId(int roleId, int sbmId) {
        Submission sbm = submissionMapper.selectByPrimaryKey(sbmId);
        ProjectInst projInst = projInstMapper.selectPIByProjIdAndStdRoleId(sbm.getProjId(), roleId);
        if (projInst == null) {
            return new SvResult<>("No team yet", null);
        }
        SubmissionInst sbmInst = sbmInstMapper.selectBySbmIdAndProjInstId(sbmId, projInst.getProjInstId());
        return new SvResult<>("", sbmInst);
    }

    @Override
    public Submission getSbmBySbmId(int sbmId) {
        return submissionMapper.selectByPrimaryKey(sbmId);
    }
}
