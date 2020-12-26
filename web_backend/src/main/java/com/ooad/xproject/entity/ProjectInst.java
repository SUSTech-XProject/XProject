package com.ooad.xproject.entity;

public class ProjectInst {
    private Integer projInstId;

    private Integer projId;

    private String topicStr;

    private String description;

    private String status;

    private String flags;

    private String teamName;

    private Integer targetMemNum;

    private String iconUrl;

    public Integer getProjInstId() {
        return projInstId;
    }

    public void setProjInstId(Integer projInstId) {
        this.projInstId = projInstId;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getTopicStr() {
        return topicStr;
    }

    public void setTopicStr(String topicStr) {
        this.topicStr = topicStr == null ? null : topicStr.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags == null ? null : flags.trim();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    public Integer getTargetMemNum() {
        return targetMemNum;
    }

    public void setTargetMemNum(Integer targetMemNum) {
        this.targetMemNum = targetMemNum;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? null : iconUrl.trim();
    }
}