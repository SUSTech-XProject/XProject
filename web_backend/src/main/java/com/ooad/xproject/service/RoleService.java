package com.ooad.xproject.service;

import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.entity.Role;

public interface RoleService {
    boolean exist(String username);

    SvResult<Role> checkRolePwd(String username, String password);

    void createUser(String type, String username, String password);
}
