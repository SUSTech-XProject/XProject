package com.ooad.xproject.vo;

import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.School;
import com.ooad.xproject.entity.Student;
import lombok.Data;

@Data
public class AccountInfoStdVO {
    private Role role;
    private Student student;
    private School school;

    public static AccountInfoStdVO createFrom(Role role, Student student, School school) {
        AccountInfoStdVO ret = new AccountInfoStdVO();

        role.setPassword(null);
        role.setSalt(null);

        ret.role = role;
        ret.student = student;
        ret.school = school;

        return ret;
    }
}

