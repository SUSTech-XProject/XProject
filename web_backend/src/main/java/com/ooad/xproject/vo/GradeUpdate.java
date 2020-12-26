package com.ooad.xproject.vo;

import com.ooad.xproject.entity.RecordInst;
import lombok.Data;

@Data
public class GradeUpdate {
    private Integer roleId;
    private String content;
    private String comments;

    public void copyToRecordInst(RecordInst recordInst) {
        recordInst.setContent(content);
        recordInst.setComments(comments);
    }
}