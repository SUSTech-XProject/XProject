package com.ooad.xproject.entity;

import java.util.Date;

public class Submission {
    private Integer sbmId;

    private Integer projId;

    private Integer creatorId;

    private String title;

    private String description;

    private Date due;

    private Date modifiedTime;

    public Integer getSbmId() {
        return sbmId;
    }

    public void setSbmId(Integer sbmId) {
        this.sbmId = sbmId;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}