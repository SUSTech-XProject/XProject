package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.RoleGroup;

public interface RoleGroupMapper {
    int deleteByPrimaryKey(Integer rgpId);

    int insert(RoleGroup record);

    int insertSelective(RoleGroup record);

    RoleGroup selectByPrimaryKey(Integer rgpId);

    int updateByPrimaryKeySelective(RoleGroup record);

    int updateByPrimaryKey(RoleGroup record);
}