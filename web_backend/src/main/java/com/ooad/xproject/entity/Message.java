package com.ooad.xproject.entity;

import java.util.Date;

public class Message {
    private Integer msgId;

    private Integer projInstId;

    private String type;

    private String title;

    private String content;

    private Integer creatorRoleId;

    private Date createdTime;

    private String status;

    private Integer roleId;

    private Integer projId;

    private Integer handlerRoleId;

    private Boolean confirmed;

    private Boolean decided;

    private String result;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getProjInstId() {
        return projInstId;
    }

    public void setProjInstId(Integer projInstId) {
        this.projInstId = projInstId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getCreatorRoleId() {
        return creatorRoleId;
    }

    public void setCreatorRoleId(Integer creatorRoleId) {
        this.creatorRoleId = creatorRoleId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public Integer getHandlerRoleId() {
        return handlerRoleId;
    }

    public void setHandlerRoleId(Integer handlerRoleId) {
        this.handlerRoleId = handlerRoleId;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getDecided() {
        return decided;
    }

    public void setDecided(Boolean decided) {
        this.decided = decided;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }
}