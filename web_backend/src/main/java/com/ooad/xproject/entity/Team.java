package com.ooad.xproject.entity;

public class Team {
    private Integer teamId;

    private Integer tgpId;

    private String teamCode;

    private String status;

    private String flags;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTgpId() {
        return tgpId;
    }

    public void setTgpId(Integer tgpId) {
        this.tgpId = tgpId;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode == null ? null : teamCode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags == null ? null : flags.trim();
    }
}