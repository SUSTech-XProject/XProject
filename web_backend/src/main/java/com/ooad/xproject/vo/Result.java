package com.ooad.xproject.vo;

import com.ooad.xproject.constant.RespStatus;

public class Result<T> {
    private int code;
    private String phrase;
    private String message;
    private T data;

    public Result(RespStatus status, String message, T data) {
        this.code = status.code;
        this.phrase = status.phrase;
        this.message = message;
        this.data = data;
    }

    public Result(RespStatus status) {
        this(status, null, null);
    }

    public Result(RespStatus status, String message) {
        this(status, message, null);
    }

    public Result(T data) {
        this(RespStatus.SUCCESS, null, data);
    }

    public Result(String message, T data) {
        this(RespStatus.SUCCESS, message, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", phrase='" + phrase + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}