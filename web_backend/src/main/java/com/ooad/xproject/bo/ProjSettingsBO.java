package com.ooad.xproject.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ProjSettingsBO {

    @JSONField(name="use_recruit", ordinal=0)
    private Boolean useRecruitSystem;

    @JSONField(name="min_size", ordinal=1)
    private Integer minSize;

    @JSONField(name="max_size", ordinal=2)
    private Integer maxSize;

    @JSONField(name="due_time", ordinal=3, format="yyyy-MM-dd HH:mm:ss")
    private Timestamp dueTime;

    @JSONField(name="allow_cross_mark", ordinal=4)
    private Boolean allowCrossMark;
}
