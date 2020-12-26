package com.ooad.xproject.dto;

import com.ooad.xproject.entity.Submission;
import com.ooad.xproject.entity.Teacher;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SbmDTO {
    private Submission submission;
    private Teacher teacher;
}
