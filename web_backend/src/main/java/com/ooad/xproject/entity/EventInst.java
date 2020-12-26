package com.ooad.xproject.entity;

import java.util.Date;

public class EventInst {
    private Integer eventId;

    private Date date;

    private Date startTime;

    private Date endTime;

    private Integer eaTaskId;

    private String week;

    private String status;

    private Integer projInstId;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getEaTaskId() {
        return eaTaskId;
    }

    public void setEaTaskId(Integer eaTaskId) {
        this.eaTaskId = eaTaskId;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getProjInstId() {
        return projInstId;
    }

    public void setProjInstId(Integer projInstId) {
        this.projInstId = projInstId;
    }
}