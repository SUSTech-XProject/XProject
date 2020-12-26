package com.ooad.xproject.controller;

import com.ooad.xproject.entity.Role;
import com.ooad.xproject.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    private final RoleService roleService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    public AdminController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("api/admin/roles")
    public List<Role> selectAll(@RequestParam(name = "type", required = false) String roleType
            ) throws Exception {
        return roleService.selectAll(roleType);
    }
}
