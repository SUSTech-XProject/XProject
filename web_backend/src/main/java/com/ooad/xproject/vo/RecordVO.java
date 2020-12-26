package com.ooad.xproject.vo;

import com.ooad.xproject.entity.Record;
import com.ooad.xproject.entity.Teacher;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecordVO {
    private Record record;
    private Teacher creator;
}
