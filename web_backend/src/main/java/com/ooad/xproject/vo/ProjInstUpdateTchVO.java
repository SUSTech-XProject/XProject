package com.ooad.xproject.vo;

import com.ooad.xproject.entity.ProjectInst;
import lombok.Data;

/**
 * Team info comes from front-end Project updating.
 */
@Data
public class ProjInstUpdateTchVO {
    private int projInstId;

    private String teamName;
    private String topic;
    private int targetMemNum;

    public void copyToProjInst(ProjectInst projInst) {
        projInst.setTeamName(teamName);
        projInst.setTargetMemNum(targetMemNum);
        projInst.setTopicStr(topic);
    }
}

