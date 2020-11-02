package com.ooad.xproject.service.impl;

import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.mapper.RoleMapper;
import com.ooad.xproject.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    /**
     * Check the role with the username and the pwd.
     * Return Role obj if success.
     */
    @Override
    public SvResult<Role> checkRolePwd(String username, String password) {
        Role role = roleMapper.selectByUsername(username);
        if (role == null) {
            return new SvResult<>(-1, "No such username");
        }
        if (!password.equals(role.getPassword())) {
            return new SvResult<>(-1, "Wrong password");
        }
        return new SvResult<>(0, "Authentication passed", role);
    }
}
