package com.ooad.xproject.vo;

import com.ooad.xproject.entity.Record;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordCreationVO {
    private int projId;
    private String name;
    private String type;
    private String baseContent;

    public void copyToRecord(Record record) {
        record.setProjId(projId);
        record.setRcdName(name);
        record.setType(type);
        record.setBaseContent(baseContent);
    }
}
