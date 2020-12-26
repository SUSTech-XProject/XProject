package com.ooad.xproject.entity;

public class Permission {
    private Integer pmsId;

    private String pmsName;

    private String pmsDesc;

    private String url;

    public Integer getPmsId() {
        return pmsId;
    }

    public void setPmsId(Integer pmsId) {
        this.pmsId = pmsId;
    }

    public String getPmsName() {
        return pmsName;
    }

    public void setPmsName(String pmsName) {
        this.pmsName = pmsName == null ? null : pmsName.trim();
    }

    public String getPmsDesc() {
        return pmsDesc;
    }

    public void setPmsDesc(String pmsDesc) {
        this.pmsDesc = pmsDesc == null ? null : pmsDesc.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}