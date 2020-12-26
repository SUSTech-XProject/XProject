package com.ooad.xproject.service;

import com.github.pagehelper.PageInfo;
import com.ooad.xproject.bo.StudentImportBO;
import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.dto.StudentDTO;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.Student;
import com.ooad.xproject.entity.Teacher;
import com.ooad.xproject.vo.AcInfoStdUpdateVO;
import com.ooad.xproject.vo.SelectorStdVO;
import com.ooad.xproject.vo.StdUpdateVO;

import java.util.List;

public interface StudentService {

    Student getStudent(Integer sid);

    // list all the student groups
//    ArrayList<Integer> getStdGroupList();

    // select by group id, return all the students of this group
    List<Student> getStudentList(Integer group);

    // select by team id, return the teammates
    List<Student> getTeammatesByTeamId(Integer tid);

    List<Student> getTeammatesByStdId(Integer sid);

    Student getStudentByRoleId(Integer roleId);

    boolean updateAcInfo(Role role, AcInfoStdUpdateVO acInfoStdUpdateVO);

    SvResult<PageInfo<StudentDTO>> getStudentListBySelector(Role role, Teacher teacher, SelectorStdVO selectorStdVO);

    List<StudentDTO> getAllStudentList(Teacher teacher);

    SvResult<Role> creatRoleAndStudent(int schId, StudentImportBO studentImportBO);

    Student getStudentByStdNo(String stdNo);

    boolean appendStdPayload(int roleId, String payload);

    boolean updateStdInfo(StdUpdateVO stdUpdateVO);
}
