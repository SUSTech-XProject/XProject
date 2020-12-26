package com.ooad.xproject.entity;

import java.util.Date;

public class Project {
    private Integer projId;

    private Integer schId;

    private Integer creatorId;

    private Date createTime;

    private String projName;

    private String courseName;

    private String term;

    private String description;

    private String topics;

    private String projSettings;

    private Boolean stdJoin;

    private Boolean tchJoin;

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName == null ? null : projName.trim();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics == null ? null : topics.trim();
    }

    public String getProjSettings() {
        return projSettings;
    }

    public void setProjSettings(String projSettings) {
        this.projSettings = projSettings == null ? null : projSettings.trim();
    }

    public Boolean getStdJoin() {
        return stdJoin;
    }

    public void setStdJoin(Boolean stdJoin) {
        this.stdJoin = stdJoin;
    }

    public Boolean getTchJoin() {
        return tchJoin;
    }

    public void setTchJoin(Boolean tchJoin) {
        this.tchJoin = tchJoin;
    }
}