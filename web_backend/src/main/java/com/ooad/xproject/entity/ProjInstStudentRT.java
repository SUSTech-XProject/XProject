package com.ooad.xproject.entity;

import java.util.Date;

public class ProjInstStudentRT {
    private Integer projInstRtId;

    private Integer projInstId;

    private Integer stdRoleId;

    private String opt;

    private Date optTime;

    public Integer getProjInstRtId() {
        return projInstRtId;
    }

    public void setProjInstRtId(Integer projInstRtId) {
        this.projInstRtId = projInstRtId;
    }

    public Integer getProjInstId() {
        return projInstId;
    }

    public void setProjInstId(Integer projInstId) {
        this.projInstId = projInstId;
    }

    public Integer getStdRoleId() {
        return stdRoleId;
    }

    public void setStdRoleId(Integer stdRoleId) {
        this.stdRoleId = stdRoleId;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt == null ? null : opt.trim();
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }
}