package com.ooad.xproject.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {
    private Integer msgId;
    private Integer projInstId;
    private String type;
    private String title;
    private String content;
    private Integer creatorRoleId;
    private Date createdTime;
    private String status;
    private Integer roleId;
    private Integer projId;
    private Integer handlerRoleId;
    private Boolean confirmed;
    private Boolean decided;
    private String result;

    private String crtRoleType;
    private String crtUsername;
    private String crtStdName;
//    private Date crtRegisterTime;
    private String crtIconUrl;

    private String hdlRoleType;
    private String hdlUsername;
    private String hdlStdName;
//    private Date hdlRegisterTime;
    private String hdlIconUrl;
}