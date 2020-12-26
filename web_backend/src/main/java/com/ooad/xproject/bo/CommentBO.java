package com.ooad.xproject.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentBO {
    @JSONField(name = "comments", ordinal = 0)
    private List<String> comments;
}
