package com.ooad.xproject.entity;

import java.util.Date;

public class Announcement {
    private Integer annId;

    private Integer projId;

    private Integer creatorId;

    private String title;

    private String message;

    private Date modifiedDate;

    public Integer getAnnId() {
        return annId;
    }

    public void setAnnId(Integer annId) {
        this.annId = annId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}