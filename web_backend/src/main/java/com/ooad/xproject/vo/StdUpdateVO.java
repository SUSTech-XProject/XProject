package com.ooad.xproject.vo;

import com.ooad.xproject.entity.Student;
import lombok.Data;

@Data
public class StdUpdateVO {
    private Integer roleId;
    private String stdName;
    private String stdNo;
    private String stdClass;
    private String email;

    public void copyToStudent(Student std) {
        std.setStdName(stdName);
        std.setStdNo(stdNo);
        std.setStdClass(stdClass);
        std.setEmail(email);
    }
}

