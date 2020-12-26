package com.ooad.xproject.vo;

import lombok.Data;

/**
 * Team info comes from front-end Project creation.
 */
@Data
public class ProjInstCreationVO {
    private int projId;
    private String teamName;
    private String topic;
    private int idealSize;
    private String description;

    private boolean useIncrNum;     // if true, replace the %d in the team name to an auto incr number
    private int generateNum;     // create ? team once
}

