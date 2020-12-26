package com.ooad.xproject.vo;

import lombok.Data;

@Data
public class CombineRcdInstParamVO {
    String recordName;
    private int[] rcdIdList;
    private double[] coeList;
}

