package com.ooad.xproject.entity;

public class RoleGroup {
    private Integer rgpId;

    private Integer roleId;

    private String groupName;

    private String description;

    public Integer getRgpId() {
        return rgpId;
    }

    public void setRgpId(Integer rgpId) {
        this.rgpId = rgpId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}