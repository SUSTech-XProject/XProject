package com.ooad.xproject.vo;

import com.ooad.xproject.entity.ProjectInst;
import lombok.Data;

import java.util.Arrays;

@Data
public class AutoFormingVO {
    private FormingTeamVO[] teamList;
    private int[] stdRoleIdList;
    private String strategy;
    private boolean autoConfirm;

    public ProjectInst[] getProjInstList() {
        return Arrays.stream(teamList).map(FormingTeamVO::toProjectInst).toArray(ProjectInst[]::new);
    }
}
