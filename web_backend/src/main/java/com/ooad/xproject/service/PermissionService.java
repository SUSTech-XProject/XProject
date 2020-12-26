package com.ooad.xproject.service;

import com.ooad.xproject.entity.Role;

import java.util.Set;

public interface PermissionService {
    Set<String> listPmsURLsByUsername(String username);

    boolean needFilter(String requestAPI);

    boolean appendPmsRoleToNewRole(Role newRole);
}
