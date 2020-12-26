package com.ooad.xproject.entity;

@Deprecated
public class StdGroup {
    private Integer sgpId;

    private Integer schId;

    private String groupName;

    public Integer getSgpId() {
        return sgpId;
    }

    public void setSgpId(Integer sgpId) {
        this.sgpId = sgpId;
    }

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }
}