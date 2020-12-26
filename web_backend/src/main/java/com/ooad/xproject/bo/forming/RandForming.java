package com.ooad.xproject.bo.forming;

import com.ooad.xproject.entity.ProjectInst;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandForming implements FormingStrategy {
    @Override
    public FormingResultBO forming(ProjectInst[] projInstList, int[] stdRoleIdList) {
        List<Pair<Integer, Integer>> matchList = new ArrayList<>();
        Random random = new Random();
        List<ProjectInst> piList = new ArrayList<>(Arrays.asList(projInstList));

        int sum = piList.stream().mapToInt(ProjectInst::getTargetMemNum).sum();

        if (sum < stdRoleIdList.length) {
            int teamListLen = piList.size();
            for (int stdRoleId: stdRoleIdList) {
                int rand = random.nextInt(teamListLen);
                ProjectInst projInst = piList.get(rand);
                matchList.add(new Pair<>(projInst.getProjInstId(), stdRoleId));
            }
        } else {
            int idx = 0;
            List<Integer> cntList = new ArrayList<>();
            for (int i = 0; i < piList.size(); i++) {
                cntList.add(0);
            }
            while (idx < stdRoleIdList.length) {
                int rand = random.nextInt(cntList.size());
                ProjectInst projInst = piList.get(rand);
                if (projInst.getTargetMemNum() <= cntList.get(rand)) {
                    piList.remove(rand);
                    cntList.remove(rand);
                } else {
                    int stdRoleId = stdRoleIdList[idx];
                    idx += 1;
                    cntList.set(rand, cntList.get(rand) + 1);
                    matchList.add(new Pair<>(projInst.getProjInstId(), stdRoleId));
                }
            }
        }

        return FormingResultBO.builder()
                .matchList(matchList)
                .message("Forming finished. Use Rand forming.")
                .successCnt(stdRoleIdList.length)
                .failCnt(0)
                .build();
    }
}
