package com.ooad.xproject.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RecordInstDTO {
    private int rcdId;
    private int rcdInstId;
    private String rcdName;
    private String type;
    private Timestamp createdTime;
    private Timestamp modifiedTime;
    private String derived;
    private String content;
    private String baseContent;
    private String comments;

    private String tchName;
    private String email;

    private String modifiedTchName;
    private String modifiedTchEmail;
}
