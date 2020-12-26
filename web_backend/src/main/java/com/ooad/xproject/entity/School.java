package com.ooad.xproject.entity;

public class School {
    private Integer schId;

    private String schName;

    private String location;

    private Boolean stdCreate;

    private Boolean tchCreate;

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName == null ? null : schName.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Boolean getStdCreate() {
        return stdCreate;
    }

    public void setStdCreate(Boolean stdCreate) {
        this.stdCreate = stdCreate;
    }

    public Boolean getTchCreate() {
        return tchCreate;
    }

    public void setTchCreate(Boolean tchCreate) {
        this.tchCreate = tchCreate;
    }
}