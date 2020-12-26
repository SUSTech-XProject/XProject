package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer pmsId);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer pmsId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    // extra

    List<Permission> getAllPmsByUsername(String username);

    boolean containPmsUrl(String requestAPI);

    int insertRolePmsRoleRT(Integer roleId, int pmsRoleId);
}