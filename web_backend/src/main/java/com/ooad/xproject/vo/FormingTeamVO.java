package com.ooad.xproject.vo;

import com.ooad.xproject.entity.ProjectInst;
import lombok.Data;

@Data
public class FormingTeamVO {
    private int index;      // projInstId
    private String name;    // teamName
    private String topic;
    private int targetMemNum;
    private int curMem;
    private String status;

    public ProjectInst toProjectInst() {
        ProjectInst ret = new ProjectInst();
        ret.setProjInstId(index);
        ret.setTeamName(name);
        ret.setTopicStr(topic);
        ret.setTargetMemNum(targetMemNum);
        ret.setStatus(status);
        return ret;
    }
}
