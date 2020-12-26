package com.ooad.xproject.entity;

import java.util.Date;

public class SubmissionInst {
    private Integer sbmInstId;

    private Integer sbmId;

    private Integer projInstId;

    private Date submitTime;

    private Integer submitterId;

    private Integer sbmLeft;

    private String attachments;

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

    public Integer getProjInstId() {
        return projInstId;
    }

    public void setProjInstId(Integer projInstId) {
        this.projInstId = projInstId;
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

    public Integer getSbmLeft() {
        return sbmLeft;
    }

    public void setSbmLeft(Integer sbmLeft) {
        this.sbmLeft = sbmLeft;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments == null ? null : attachments.trim();
    }
}