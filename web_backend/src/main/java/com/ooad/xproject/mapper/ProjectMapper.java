package com.ooad.xproject.mapper;

import com.ooad.xproject.bo.RecordUnitBO;
import com.ooad.xproject.entity.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper {
    int deleteByPrimaryKey(Integer projId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer projId);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    List<Project> selectByCreatorId(Integer tchId);

    List<Project> selectByRoleId(Integer roleId);

    List<Project> selectAll();

    List<Project> selectBySchId(Integer schId);

    int joinProject(int roleId, int projId, String groupMark);

    List<RecordUnitBO> selectRecordUnitByProjId(Integer pid);

    List<Integer> selectByProjAndRole(Integer projId, Integer roleId);

    int updateProjectRoleRT(Integer prrId, String groupMark);

    int insertProjectRoleRT(Integer projId, Integer roleId, String groupMark);

    int quitProject(Integer roleId, int projId);

    boolean selectExistByRoleIdAndProjId(int roleId, int projId);
}