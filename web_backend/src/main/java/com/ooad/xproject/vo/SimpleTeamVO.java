package com.ooad.xproject.vo;

import com.ooad.xproject.entity.ProjectInst;
import lombok.Data;

/**
 * Simplified team view obj shown in card
 */
@Data
public class SimpleTeamVO {
    private int projInstId;
    private String teamName;
    private String topic;
    private String status;
    private String tags;
    private String descriptions;
    private int targetMemNum;
    private int curMemNum;
    private String iconUrl;

    public static SimpleTeamVO createFrom(ProjectInst projectInst, int curMemNum) {
        SimpleTeamVO ret = new SimpleTeamVO();
        ret.projInstId = projectInst.getProjInstId();
        ret.teamName = projectInst.getTeamName();
        ret.topic = projectInst.getTopicStr();
        ret.status = projectInst.getStatus();
        ret.tags = projectInst.getFlags();
        ret.descriptions = projectInst.getDescription();
        ret.targetMemNum = projectInst.getTargetMemNum();
        ret.curMemNum = curMemNum;
        ret.iconUrl = projectInst.getIconUrl();
        return ret;
    }
}
