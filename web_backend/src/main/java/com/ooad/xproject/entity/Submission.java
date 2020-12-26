package com.ooad.xproject.entity;

import java.util.Date;

public class Submission {
    private Integer sbmId;

    private Integer projId;

    private Integer creatorId;

    private String title;

    private String description;

    private Date modifiedTime;

    private Date createdTime;

    private Date dueTime;

    private Date finalTime;

    private Integer maxSbm;

    private String status;

    private String resources;

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

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
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

    public Date getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(Date finalTime) {
        this.finalTime = finalTime;
    }

    public Integer getMaxSbm() {
        return maxSbm;
    }

    public void setMaxSbm(Integer maxSbm) {
        this.maxSbm = maxSbm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources == null ? null : resources.trim();
    }
}