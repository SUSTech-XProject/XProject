package com.ooad.xproject.constant;

public enum RespStatus {
    Status200(200, "OK"),
    Status400(400, "Bad Request"),
    Status401(401, "Unauthorized"),
    Status404(404, "Not Found"),
    Status500(500, "Internal Server Error"),
    Status501(501, "Not Implemented");

    public int code;
    public String phrase;

    RespStatus(int code, String phrase) {
        this.code = code;
        this.phrase = phrase;
    }
}
