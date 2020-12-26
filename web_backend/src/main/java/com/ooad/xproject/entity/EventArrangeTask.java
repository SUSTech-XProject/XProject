package com.ooad.xproject.entity;

import java.util.Date;

public class EventArrangeTask {
    private Integer eaTaskId;

    private Integer projId;

    private Integer creatorId;

    private String title;

    private String description;

    private Boolean stdAdaptable;

    private Date createdTime;

    private Date dueTime;

    private String status;

    public Integer getEaTaskId() {
        return eaTaskId;
    }

    public void setEaTaskId(Integer eaTaskId) {
        this.eaTaskId = eaTaskId;
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

    public Boolean getStdAdaptable() {
        return stdAdaptable;
    }

    public void setStdAdaptable(Boolean stdAdaptable) {
        this.stdAdaptable = stdAdaptable;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}