package com.ooad.xproject.service;

import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.entity.Role;

public interface RoleService {
    SvResult<Role> checkRolePwd(String username, String password);
}
