package com.ooad.xproject.vo;

import lombok.Data;

/**
 * Role info comes from front-end register.
 */
@Data
public class RoleVO {
    private String type;      // role type
    private String username;
    private String password;
    private Integer schId;
}

