package com.ooad.xproject.entity;

public class RecordInst {
    private Integer rcdInstId;

    private Integer rcdId;

    private Integer stdId;

    private String content;

    public Integer getRcdInstId() {
        return rcdInstId;
    }

    public void setRcdInstId(Integer rcdInstId) {
        this.rcdInstId = rcdInstId;
    }

    public Integer getRcdId() {
        return rcdId;
    }

    public void setRcdId(Integer rcdId) {
        this.rcdId = rcdId;
    }

    public Integer getStdId() {
        return stdId;
    }

    public void setStdId(Integer stdId) {
        this.stdId = stdId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}