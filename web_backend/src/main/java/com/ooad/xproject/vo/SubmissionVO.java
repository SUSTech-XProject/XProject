package com.ooad.xproject.vo;

import com.ooad.xproject.entity.Submission;
import com.ooad.xproject.utils.TimeUtils;
import lombok.Data;

import java.text.ParseException;

@Data
public class SubmissionVO {
    private int sbmId;      // used when update

    private int projId;
    private String title;
    private String description;
    private String dueTime;     // standard time format yyyy-MM-dd HH:mm:ss
    private String finalTime;     // standard time format yyyy-MM-dd HH:mm:ss
    private int maxSbm;
    private String status;

    public void copyToSubmission(Submission submission, Integer creatorRoleId) throws ParseException {
        submission.setProjId(projId);
        submission.setTitle(title);
        submission.setDescription(description);
        if (dueTime != null) {
            submission.setDueTime(TimeUtils.strToDateStandard(dueTime));
        }
        if (finalTime != null) {
            submission.setFinalTime(TimeUtils.strToDateStandard(finalTime));
        }
        submission.setMaxSbm(maxSbm);
        submission.setStatus(status);

        submission.setCreatorId(creatorRoleId);
    }
}

