package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    // extra

    Role selectByUsername(String userName);

    Role selectByUsername1(String userName);
}