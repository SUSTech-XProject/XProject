package com.ooad.xproject.service.impl;

import com.ooad.xproject.constant.RoleType;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.service.PermissionService;
import com.ooad.xproject.service.RoleService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Test
    public void testSalt() {
        System.out.println(new SecureRandomNumberGenerator().nextBytes().toString());
    }

    @Test
    public void createAdmin() {
        Role newRole = roleService.createUser(RoleType.Admin.name(), "administrator0", "administrator0", null);
        permissionService.appendPmsRoleToNewRole(newRole);
    }
}