package com.ooad.xproject.bo.forming;

import com.ooad.xproject.entity.ProjectInst;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TeamFirstForming implements FormingStrategy {
    @Override
    public FormingResultBO forming(ProjectInst[] projInstList, int[] stdRoleIdList) {
        List<Pair<Integer, Integer>> matchList = new ArrayList<>();
        int successCnt = 0;

        outer:
        for(ProjectInst projInst: projInstList) {
            int targetMemNum = projInst.getTargetMemNum();
            for (int i = 0; i < targetMemNum; i++) {
                if (successCnt >= stdRoleIdList.length) {
                    break outer;
                }
                int stdRoleId = stdRoleIdList[successCnt];
                matchList.add(new Pair<>(projInst.getProjInstId(), stdRoleId));
                successCnt += 1;
            }
        }

        return FormingResultBO.builder()
                .matchList(matchList)
                .message("Forming finished. Use Team First.")
                .successCnt(successCnt)
                .failCnt(stdRoleIdList.length - successCnt)
                .build();
    }
}
