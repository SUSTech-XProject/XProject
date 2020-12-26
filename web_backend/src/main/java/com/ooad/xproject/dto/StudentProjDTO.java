package com.ooad.xproject.dto;

import lombok.Data;

@Data
public class StudentProjDTO {
    private Integer stdId;
    private Integer roleId;
    private String username;
    private String stdName;
    private String stdNo;
    private String stdClass;
    private String flags;
    private Integer schId;
    private String email;
    private String iconUrl;

    private String groupMark;
    private Integer projInstId;
    private String teamName;
    private String topicStr;
    private String status;
}
