package com.ooad.xproject.entity;

public class Student {
    private Integer stdId;

    private Integer roleId;

    private String stdName;

    private String stdNo;

    private String stdClass;

    private String flags;

    private Integer schId;

    private String email;

    private String skills;

    private String bio;

    private String payload;

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

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills == null ? null : skills.trim();
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio == null ? null : bio.trim();
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload == null ? null : payload.trim();
    }
}