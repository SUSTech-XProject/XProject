package com.ooad.xproject.vo;

import lombok.Data;

@Data
public class SelectorStdVO {
    private int page = 1;
    private int pageSize = 20;
    private String orderBy = "std_id";
    private String orderType = "asc";
    private String stdClass;

    public String getOrderStr() {
        return orderBy + " " + orderType;
    }
}

