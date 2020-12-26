package com.ooad.xproject.vo;

import com.ooad.xproject.entity.ProjectInst;
import lombok.Data;

/**
 * Team info comes from front-end Project updating.
 */
@Data
public class ProjInstUpdateVO {
    private int projId;

    private String teamName;
    private String description;
    private Integer targetMemNum;
    private String tags;        // json string
    private String topic;

    public void copyToProjInst(ProjectInst projInst) {
        projInst.setTeamName(teamName);
        projInst.setDescription(description);
        projInst.setTargetMemNum(targetMemNum);
        projInst.setFlags(tags);
        projInst.setTopicStr(topic);
    }
}

