package com.ooad.xproject.mapper;

import com.ooad.xproject.dto.StudentDTO;
import com.ooad.xproject.dto.StudentProjDTO;
import com.ooad.xproject.entity.Student;
import com.ooad.xproject.entity.StudentExample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentMapper {
    int deleteByPrimaryKey(Integer stdId);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(Integer stdId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    Student selectByRoleId(Integer rid);

    List<Student> selectBySgpId(Integer sgpId);

    List<Student> selectByTeamId(Integer tid);

    int selectTeamIdByStdId(Integer sid);

    List<StudentDTO> selectDTOBySelector(Integer schId, String stdClass);

    List<StudentProjDTO> selectProjStudents(int projId);

    Student selectByStdNo(String stdNo);
}