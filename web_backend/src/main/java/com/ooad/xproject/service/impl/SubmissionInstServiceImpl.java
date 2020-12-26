package com.ooad.xproject.service.impl;

import com.ooad.xproject.entity.Submission;
import com.ooad.xproject.entity.SubmissionInst;
import com.ooad.xproject.mapper.SubmissionInstMapper;
import com.ooad.xproject.mapper.SubmissionMapper;
import com.ooad.xproject.service.SubmissionInstService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubmissionInstServiceImpl implements SubmissionInstService {
    private final SubmissionInstMapper submissionInstMapper;

    private final SubmissionMapper submissionMapper;

    public SubmissionInstServiceImpl(SubmissionInstMapper submissionInstMapper, SubmissionMapper submissionMapper) {
        this.submissionInstMapper = submissionInstMapper;
        this.submissionMapper = submissionMapper;
    }

    @Override
    public int upsertSubmissionInst(SubmissionInst submissionInst) {

        SubmissionInst sbmInst = submissionInstMapper.selectBySbmIdAndProjInstId(submissionInst.getSbmId(), submissionInst.getProjInstId());
        if (sbmInst == null) {
            Submission sbm = submissionMapper.selectByPrimaryKey(submissionInst.getSbmId());
            if(sbm.getDueTime().before(new Date(System.currentTimeMillis()))){
                return 0;
            }
            sbmInst = new SubmissionInst();
            sbmInst.setSbmId(submissionInst.getSbmId());
            sbmInst.setProjInstId(submissionInst.getProjInstId());
            sbmInst.setSubmitterId(submissionInst.getSubmitterId());
            sbmInst.setAttachments(submissionInst.getAttachments());
            sbmInst.setSbmLeft(sbm.getMaxSbm() - 1);
            return submissionInstMapper.insert(sbmInst);
        } else {
            if (sbmInst.getSbmLeft() == -1) {
                sbmInst.setSubmitterId(submissionInst.getSubmitterId());
                sbmInst.setAttachments(submissionInst.getAttachments());
                return submissionInstMapper.updateSubmissionInst(sbmInst);
            } else if (sbmInst.getSbmLeft() == 0) {
                return 0;
            } else {
                sbmInst.setSubmitterId(submissionInst.getSubmitterId());
                sbmInst.setSbmLeft(sbmInst.getSbmLeft() - 1);
                sbmInst.setAttachments(submissionInst.getAttachments());
                return submissionInstMapper.updateSubmissionInst(sbmInst);
            }
        }
    }

    @Override
    public List<SubmissionInst> getSubmissionInstList(int sbmId, int projId) {
        return submissionInstMapper.selectBySbmIdAndProjId(sbmId, projId);
    }

}
