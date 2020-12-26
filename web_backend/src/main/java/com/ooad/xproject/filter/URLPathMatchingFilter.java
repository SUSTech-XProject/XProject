package com.ooad.xproject.filter;

import com.ooad.xproject.entity.Role;
import com.ooad.xproject.service.PermissionService;
import com.ooad.xproject.service.RoleService;
import com.ooad.xproject.utils.SpringContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class URLPathMatchingFilter extends PathMatchingFilter {

    private PermissionService adminPermissionService;
    private RoleService roleService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (HttpMethod.OPTIONS.toString().equals((httpServletRequest).getMethod())) {
            httpServletResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        if (null==adminPermissionService) {
            adminPermissionService = SpringContextUtils.getContext().getBean(PermissionService.class);
        }
        if (null==roleService) {
            roleService = SpringContextUtils.getContext().getBean(RoleService.class);
        }

        String requestAPI = getPathWithinApplication(request);
//        System.out.println("Visit interface" + requestAPI);

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            System.out.println("Need login");
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

//        System.out.println("Verify access rights: " + requestAPI);

        String username = subject.getPrincipal().toString();
        // check valid
        Role role = roleService.getByUsername(username);
        if ("Disabled".equals(role.getStatus())) {
            System.out.println("The current user is disabled by admin" + requestAPI);
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 判断当前用户是否有相应权限
        Set<String> permissionAPIs = adminPermissionService.listPmsURLsByUsername(username);
        boolean hasPermission = checkPermission(requestAPI, permissionAPIs);

        if (hasPermission) {
//            System.out.println("Access right: " + requestAPI + "verify successfully");
            return true;
        } else {
            System.out.println("The current user has no right to access interface " + requestAPI);
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    private boolean checkPermission(String requestAPI, Set<String> permissionAPIs) {
        for (String permission : permissionAPIs) {
            if (permission.contains("**")) {
                permission = permission.replace("**", "");
                if (requestAPI.startsWith(permission)) {
                    return true;
                }
            } else {
                if (permission.equals(requestAPI)) {
                    return true;
                }
            }
        }
        return false;
    }
}