package com.ooad.xproject.entity;

public class TestObject {
    private Integer id;

    private String str;

    private Integer num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str == null ? null : str.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}