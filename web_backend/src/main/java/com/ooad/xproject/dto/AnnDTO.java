package com.ooad.xproject.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AnnDTO {
    private Integer annId;

    private Integer projId;

    private Integer creatorId;

    private String title;

    private String message;

    private Date modifiedTime;

    private Date createdTime;

    private String tchName;

    private String email;
}
