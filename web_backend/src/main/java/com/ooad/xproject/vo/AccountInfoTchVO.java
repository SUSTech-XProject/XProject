package com.ooad.xproject.vo;

import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.School;
import com.ooad.xproject.entity.Teacher;
import lombok.Data;

@Data
public class AccountInfoTchVO {
    private Role role;
    private Teacher teacher;
    private School school;

    public static AccountInfoTchVO createFrom(Role role, Teacher teacher, School school) {
        AccountInfoTchVO ret = new AccountInfoTchVO();

        role.setPassword(null);
        role.setSalt(null);

        ret.role = role;
        ret.teacher = teacher;
        ret.school = school;

        return ret;
    }
}

