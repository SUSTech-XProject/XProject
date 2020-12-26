package com.ooad.xproject.vo;

import com.ooad.xproject.entity.Student;
import com.ooad.xproject.entity.SubmissionInst;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SbmInstVO {
    private SubmissionInst submissionInst;
    private Student submitter;
}

