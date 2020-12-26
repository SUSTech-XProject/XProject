package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.PmsRole;
import org.springframework.stereotype.Repository;

@Repository
public interface PmsRoleMapper {
    int deleteByPrimaryKey(Integer pmsRoleId);

    int insert(PmsRole record);

    int insertSelective(PmsRole record);

    PmsRole selectByPrimaryKey(Integer pmsRoleId);

    int updateByPrimaryKeySelective(PmsRole record);

    int updateByPrimaryKey(PmsRole record);
}