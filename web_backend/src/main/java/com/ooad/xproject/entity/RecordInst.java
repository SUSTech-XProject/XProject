package com.ooad.xproject.entity;

import java.util.Date;

public class RecordInst {
    private Integer rcdInstId;

    private Integer rcdId;

    private Integer roleId;

    private Date modifiedTime;

    private String content;

    private String comments;

    private Integer modifiedRoleId;

    public Integer getRcdInstId() {
        return rcdInstId;
    }

    public void setRcdInstId(Integer rcdInstId) {
        this.rcdInstId = rcdInstId;
    }

    public Integer getRcdId() {
        return rcdId;
    }

    public void setRcdId(Integer rcdId) {
        this.rcdId = rcdId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Integer getModifiedRoleId() {
        return modifiedRoleId;
    }

    public void setModifiedRoleId(Integer modifiedRoleId) {
        this.modifiedRoleId = modifiedRoleId;
    }
}