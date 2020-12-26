package com.ooad.xproject.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicBO {
    @JSONField(name = "topic_name", ordinal = 0)
    private String topicName;

    @JSONField(name = "max_team_size", ordinal = 1)
    private int maxTeamSize;       // < 0 means no limit
}
