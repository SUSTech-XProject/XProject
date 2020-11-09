package com.ooad.xproject.entity;

public class ProjectInst {
    private Integer projInstId;

    private Integer projId;

    private Integer teamId;

    private String topicStr;

    private String description;

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

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
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
}