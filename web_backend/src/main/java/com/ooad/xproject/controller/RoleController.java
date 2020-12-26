package com.ooad.xproject.controller;

import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.constant.RoleType;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.School;
import com.ooad.xproject.service.HomeService;
import com.ooad.xproject.service.PermissionService;
import com.ooad.xproject.service.RoleService;
import com.ooad.xproject.utils.RoleUtils;
import com.ooad.xproject.vo.ChangePwdVO;
import com.ooad.xproject.vo.Result;
import com.ooad.xproject.vo.RoleVO;
import org.apache.ibatis.jdbc.Null;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class RoleController {

    private final RoleService roleService;
    private final PermissionService permissionService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());
    private final HomeService homeService;

    public RoleController(RoleService testService, PermissionService permissionService, HomeService homeService) {
        this.roleService = testService;
        this.permissionService = permissionService;
        this.homeService = homeService;
    }

    @ResponseBody
    @RequestMapping(value = "api/visitor/login", method = RequestMethod.POST)
    public Result<?> login(@RequestBody RoleVO requestRoleVO, HttpSession session) {
        String username = requestRoleVO.getUsername();
        username = HtmlUtils.htmlEscape(username);
        String password = requestRoleVO.getPassword();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);      // can get from front end

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            String msg = "Username or password error: " + e.getMessage();
            return new Result<>(RespStatus.FAIL, msg);
        }

        Role role = roleService.getByUsername(username);

        session.setAttribute("role", role);
        return new Result<>(RespStatus.SUCCESS);
    }

    @ResponseBody
    @GetMapping("api/logout")
    public Result<Null> logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String msg = "Logout successfully";
        return new Result<>(RespStatus.SUCCESS, msg);
    }

    @ResponseBody
    @PostMapping("api/visitor/register")
    public Result<?> register(@RequestBody RoleVO requestRoleVO) {
        String type = requestRoleVO.getType();
        String username = requestRoleVO.getUsername();
        username = HtmlUtils.htmlEscape(username);
        String password = requestRoleVO.getPassword();
        Integer schId = requestRoleVO.getSchId();

        Role role = roleService.getByUsername(username);
        if (role != null) {
            String msg = "Username already exists";
            logger.info(msg);
            return new Result<>(RespStatus.FAIL, msg);
        }

        if (!roleService.validUsername(username)) {
            String msg = "The username must have at least 8 characters and 24 at most. " +
                    "Don't start with a number.";
            logger.info(msg);
            return new Result<>(RespStatus.FAIL, msg);
        }
        if (!roleService.validPassword(password)) {
            String msg = "The password must have at least 8 characters and 32 at most." +
                    "It must contain letters and numbers.";
            logger.info(msg);
            return new Result<>(RespStatus.FAIL, msg);
        }

        RespStatus status;
        String msg;

        if (RoleType.Student.match(type) || RoleType.Teacher.match(type)) {
            School school = homeService.getSchool(schId);
            if (school == null) {
                return new Result<>(RespStatus.FAIL, "No such school");
            }

            Role newRole = roleService.createUser(type, username, password, schId);
            permissionService.appendPmsRoleToNewRole(newRole);
            msg = "User created";
            status = RespStatus.SUCCESS;
            logger.info(msg);

            permissionService.appendPmsRoleToNewRole(newRole);

        } else {
            msg = "Role type error";
            status = RespStatus.FAIL;
            logger.warn(msg);
        }

        return new Result<>(status, msg);
    }

    @ResponseBody
    @GetMapping("api/authentication")
    public Result<Role> authentication(){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        logger.info("Authentication for " + username);
        Role role = roleService.getByUsername(username);
        return new Result<>(role);
    }

    @ResponseBody
    @PostMapping("api/all/change/password")
    public Result<?> postChangePassword(@RequestBody ChangePwdVO changePwdVO){
        if (!changePwdVO.getNewPassword().equals(changePwdVO.getConfirmNewPassword())) {
            return new Result<>(RespStatus.FAIL, "Two passwords are not matched");
        }
        Role role = roleService.getByUsername(RoleUtils.getUsername());
        boolean success = roleService.changePwd(role, changePwdVO);
        return Result.createBoolResult(success, "Change password successfully", "Change password failed");
    }

    @ResponseBody
    @GetMapping("api/admin/role/list")
    public Result<?> getRoleList(){
        List<Role> roleList = roleService.getAllRoles();
        return new Result<>(roleList);
    }

    @ResponseBody
    @PostMapping("api/admin/role/change")
    public Result<?> postChangeStatus(@RequestParam("roleId") int roleId){
        boolean success = roleService.changeStatus(roleId);
        return Result.createBoolResult(success, "Change password successfully", "Change password failed");
    }
}
