package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ResourceMapper {
    int deleteByPrimaryKey(Integer srcId);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer srcId);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    List<Resource> selectByProjId(int projId);
}