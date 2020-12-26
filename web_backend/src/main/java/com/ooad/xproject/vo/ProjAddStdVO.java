package com.ooad.xproject.vo;

import lombok.Data;

@Data
public class ProjAddStdVO {
    private int projId;
    private String groupMark = "Default";
    private int[] stdRoleIdList;
}

