package com.ooad.xproject.dto;

import com.ooad.xproject.entity.EventInst;
import com.ooad.xproject.entity.ProjectInst;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventInstDTO {
    private EventInst eventInst;
    private ProjectInst projInst;
}
