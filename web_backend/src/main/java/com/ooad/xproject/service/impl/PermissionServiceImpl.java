package com.ooad.xproject.service.impl;

import com.ooad.xproject.constant.RoleType;
import com.ooad.xproject.entity.Permission;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.mapper.PermissionMapper;
import com.ooad.xproject.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final int PSM_ROLE_ID_ADMIN = 1;
    private final int PSM_ROLE_ID_TEACHER = 2;
    private final int PSM_ROLE_ID_STUDENT = 3;
    private final int PSM_ROLE_ID_ALL = 3;

    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Set<String> listPmsURLsByUsername(String username) {
        List<Permission> pmsList = permissionMapper.getAllPmsByUsername(username);
        return pmsList.stream().map(Permission::getUrl).collect(Collectors.toSet());
    }

    @Override
    public boolean needFilter(String requestAPI) {
        return permissionMapper.containPmsUrl(requestAPI);
    }

    @Override
    public boolean appendPmsRoleToNewRole(Role newRole) {
        int affectedRowCnt = 0;
        switch (RoleType.getRoleType(newRole.getRoleType())) {
            case Admin:
                affectedRowCnt = permissionMapper.insertRolePmsRoleRT(newRole.getRoleId(), PSM_ROLE_ID_ADMIN);
                break;
            case Teacher:
                affectedRowCnt = permissionMapper.insertRolePmsRoleRT(newRole.getRoleId(), PSM_ROLE_ID_TEACHER);
                break;
            case Student:
                affectedRowCnt = permissionMapper.insertRolePmsRoleRT(newRole.getRoleId(), PSM_ROLE_ID_STUDENT);
                break;
            case Null:
                break;
        }
        return affectedRowCnt == 1;
    }
}
