package com.ooad.xproject.entity;

public class TeamGroup {
    private Integer tgpId;

    private Integer projId;

    private String groupName;

    public Integer getTgpId() {
        return tgpId;
    }

    public void setTgpId(Integer tgpId) {
        this.tgpId = tgpId;
    }

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }
}