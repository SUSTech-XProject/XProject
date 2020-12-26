package com.ooad.xproject.bo.forming;

import com.ooad.xproject.entity.ProjectInst;

public interface FormingStrategy {
   FormingResultBO forming(ProjectInst[] projInstList, int[] stdRoleIdList);
}