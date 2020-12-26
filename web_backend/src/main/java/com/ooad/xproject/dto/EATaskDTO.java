package com.ooad.xproject.dto;

import com.ooad.xproject.entity.EventArrangeTask;
import com.ooad.xproject.entity.Teacher;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EATaskDTO {
    private EventArrangeTask eaTask;
    private Teacher creator;
}
