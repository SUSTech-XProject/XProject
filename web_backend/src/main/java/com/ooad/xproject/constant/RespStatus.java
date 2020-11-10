package com.ooad.xproject.constant;

public enum RespStatus {
    SUCCESS(200, "OK"),
    FAIL(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented");

    public int code;
    public String phrase;

    RespStatus(int code, String phrase) {
        this.code = code;
        this.phrase = phrase;
    }
}
