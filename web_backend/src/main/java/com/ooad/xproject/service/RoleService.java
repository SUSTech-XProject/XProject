package com.ooad.xproject.service;

public interface RoleService {
    boolean exist(String username);

    void createUser(String type, String username, String password);
}
