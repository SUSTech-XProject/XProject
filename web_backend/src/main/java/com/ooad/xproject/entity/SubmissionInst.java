package com.ooad.xproject.entity;

import java.util.Date;

public class SubmissionInst {
    private Integer sbmInstId;

    private Integer sbmId;

    private Integer teamId;

    private Date submitTime;

    private Integer submitterId;

    public Integer getSbmInstId() {
        return sbmInstId;
    }

    public void setSbmInstId(Integer sbmInstId) {
        this.sbmInstId = sbmInstId;
    }

    public Integer getSbmId() {
        return sbmId;
    }

    public void setSbmId(Integer sbmId) {
        this.sbmId = sbmId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(Integer submitterId) {
        this.submitterId = submitterId;
    }
}