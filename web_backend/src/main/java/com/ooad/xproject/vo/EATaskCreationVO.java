package com.ooad.xproject.vo;

import com.ooad.xproject.entity.EventArrangeTask;
import com.ooad.xproject.utils.TimeUtils;
import lombok.Data;

import java.text.ParseException;

@Data
public class EATaskCreationVO {
    private int projId;
    private String title;
    private String description;
    private String dueTime;     // standard time format yyyy-MM-dd HH:mm:ss
    private boolean stdAdaptable;

    public void copyToEATask(EventArrangeTask eaTask, int creatorRoleId) throws ParseException {
        eaTask.setProjId(projId);
        eaTask.setTitle(title);
        eaTask.setDescription(description);
        if (dueTime != null) {
            eaTask.setDueTime(TimeUtils.strToDateStandard(dueTime));
        }
        eaTask.setStdAdaptable(stdAdaptable);
        eaTask.setCreatorId(creatorRoleId);
    }
}

