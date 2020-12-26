package com.ooad.xproject.vo;

import lombok.Data;

@Data
public class RecordInstUpdateRetVO {
    private int total = 0;
    private int success = 0;
    private int fail = 0;
    private int create = 0;
    private int modify = 0;

    public void incrCreate() {
        create += 1;
    }

    public void incrModify() {
        modify += 1;
    }

    public void updateSuccessOrFail(boolean success) {
        if (success) {
            this.success += 1;
        } else {
            this.fail += 1;
        }
    }

    public String generateMessage() {
        return String.format("Grade update finished. Total %d\r\n" +
                "Success %d, failed %d\r\n" +
                "Created %d, modified %d",
                total, success, fail, create, modify);
    }
}
