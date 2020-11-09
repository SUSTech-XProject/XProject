package com.ooad.xproject.entity;

import java.util.Date;

public class TeamFormTask {
    private Integer tfTaskId;

    private Integer projId;

    private Integer tgpId;

    private Integer creatorId;

    private String title;

    private String description;

    private String mode;

    private Date modifiedDate;

    public Integer getTfTaskId() {
        return tfTaskId;
    }

    public void setTfTaskId(Integer tfTaskId) {
        this.tfTaskId = tfTaskId;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public Integer getTgpId() {
        return tgpId;
    }

    public void setTgpId(Integer tgpId) {
        this.tgpId = tgpId;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode == null ? null : mode.trim();
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}