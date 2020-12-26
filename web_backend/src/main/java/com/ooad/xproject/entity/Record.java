package com.ooad.xproject.entity;

import java.util.Date;

public class Record {
    private Integer rcdId;

    private Integer projId;

    private Integer creatorId;

    private String rcdName;

    private String type;

    private String baseContent;

    private Date createdTime;

    public Integer getRcdId() {
        return rcdId;
    }

    public void setRcdId(Integer rcdId) {
        this.rcdId = rcdId;
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

    public String getRcdName() {
        return rcdName;
    }

    public void setRcdName(String rcdName) {
        this.rcdName = rcdName == null ? null : rcdName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getBaseContent() {
        return baseContent;
    }

    public void setBaseContent(String baseContent) {
        this.baseContent = baseContent == null ? null : baseContent.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}