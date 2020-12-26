package com.ooad.xproject.service;

import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.Teacher;
import com.ooad.xproject.vo.AcInfoTchUpdateVO;

public interface TeacherService {

    Teacher getTeacherByTid(Integer tid);

    // list all the student groups
//    ArrayList<Integer> getStdGroupList();

    Teacher getTeacherByRoleId(Integer roleId);

    boolean updateAcInfo(Role role, AcInfoTchUpdateVO acInfoTchUpdateVO);
}
