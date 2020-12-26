package com.ooad.xproject.service;

import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.School;
import com.ooad.xproject.vo.ChangePwdVO;

import java.util.List;

public interface RoleService {


    Role getByUsername(String username);

    Role createUser(String type, String username, String password, Integer schId);

    boolean validUsername(String username);

    boolean validPassword(String password);

    List<Role> selectAll(String roleType);

    Role getByRoleId(int roleId);

    boolean changePwd(Role role, ChangePwdVO changePwdVO);

    List<Role> getAllRoles();

    boolean changeStatus(int roleId);

    boolean updateSchool(School school);
}
