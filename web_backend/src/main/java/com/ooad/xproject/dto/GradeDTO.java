package com.ooad.xproject.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class GradeDTO {
    private Integer stdId;
    private Integer roleId;
    private String iconUrl;
    private String stdNo;
    private String stdName;

    private String rcdInstId;
    private String type;
    private Timestamp modifiedTime;
    private String derived;
    private String content;
    private String baseContent;
    private String comments;

    private String tchName;
    private String email;
}
