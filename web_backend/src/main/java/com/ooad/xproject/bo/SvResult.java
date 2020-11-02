package com.ooad.xproject.bo;

public class SvResult<T> {
    private int code;
    private String msg;
    private T data;

    public SvResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public SvResult(int code, String msg) {
        this(code, msg, null);
    }

    public SvResult(int code, T data) {
        this(code, "", data);
    }

    public SvResult(String msg, T data) {
        this(0, msg, data);
    }

    public boolean failed() {
        return this.code < 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SvResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
