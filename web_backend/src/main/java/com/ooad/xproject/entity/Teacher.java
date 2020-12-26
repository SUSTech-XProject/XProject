package com.ooad.xproject.entity;

public class Teacher {
    private Integer tchId;

    private Integer roleId;

    private String tchName;

    private String tchNo;

    private Integer schId;

    private String email;

    public Integer getTchId() {
        return tchId;
    }

    public void setTchId(Integer tchId) {
        this.tchId = tchId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getTchName() {
        return tchName;
    }

    public void setTchName(String tchName) {
        this.tchName = tchName == null ? null : tchName.trim();
    }

    public String getTchNo() {
        return tchNo;
    }

    public void setTchNo(String tchNo) {
        this.tchNo = tchNo == null ? null : tchNo.trim();
    }

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}