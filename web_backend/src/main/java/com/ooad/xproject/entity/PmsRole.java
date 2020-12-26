package com.ooad.xproject.entity;

public class PmsRole {
    private Integer pmsRoleId;

    private String name;

    private String prDesc;

    public Integer getPmsRoleId() {
        return pmsRoleId;
    }

    public void setPmsRoleId(Integer pmsRoleId) {
        this.pmsRoleId = pmsRoleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPrDesc() {
        return prDesc;
    }

    public void setPrDesc(String prDesc) {
        this.prDesc = prDesc == null ? null : prDesc.trim();
    }
}