package com.ooad.xproject.entity;

public class Student {
    private Integer stdId;

    private Integer roleId;

    private String stdName;

    private String stdNo;

    private String stdClass;

    private String flags;

    public Integer getStdId() {
        return stdId;
    }

    public void setStdId(Integer stdId) {
        this.stdId = stdId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName == null ? null : stdName.trim();
    }

    public String getStdNo() {
        return stdNo;
    }

    public void setStdNo(String stdNo) {
        this.stdNo = stdNo == null ? null : stdNo.trim();
    }

    public String getStdClass() {
        return stdClass;
    }

    public void setStdClass(String stdClass) {
        this.stdClass = stdClass == null ? null : stdClass.trim();
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags == null ? null : flags.trim();
    }
}