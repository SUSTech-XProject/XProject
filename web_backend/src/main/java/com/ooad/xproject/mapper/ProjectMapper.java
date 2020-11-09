package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.Project;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer projId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer projId);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}