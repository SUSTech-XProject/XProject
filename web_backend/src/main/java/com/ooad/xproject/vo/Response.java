package com.ooad.xproject.vo;

import com.ooad.xproject.constant.RespStatus;

public class Response {
    private RespStatus status;

    public Response(RespStatus status) {
        this.status = status;
    }

    public RespStatus getStatus() {
        return status;
    }

    public void setStatus(RespStatus status) {
        this.status = status;
    }
}