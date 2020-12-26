package com.ooad.xproject.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

@Component
public class RoleUtils {
    public static String getUsername() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getPrincipal().toString();
    }
}
