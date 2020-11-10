package com.ooad.xproject.controller;

import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.constant.RoleType;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.service.RoleService;
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

@RestController
public class RoleController {

    private final RoleService roleService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    public RoleController(RoleService testService) {
        this.roleService = testService;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public Result<?> login(@RequestBody RoleVO requestRoleVO, HttpSession session) {
        String username = requestRoleVO.getUsername();
        username = HtmlUtils.htmlEscape(username);
        String password = requestRoleVO.getPassword();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

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

    @CrossOrigin
    @ResponseBody
    @PostMapping("api/register")
    public Result<?> register(@RequestBody RoleVO requestRoleVO) {
        String type = requestRoleVO.getType();
        String username = requestRoleVO.getUsername();
        username = HtmlUtils.htmlEscape(username);
        String password = requestRoleVO.getPassword();

        Role role = roleService.getByUsername(username);
        if (role != null) {
            String msg = "Username already exists";
            logger.info(msg);
            return new Result<Null>(RespStatus.FAIL, msg);
        }

        if (!roleService.validUsername(username)) {
            String msg = "The username must have at least 8 characters. " +
                    "Don't start with a number.";
            logger.info(msg);
            return new Result<Null>(RespStatus.FAIL, msg);
        }
        if (!roleService.validPassword(password)) {
            String msg = "The username must have at least 8 characters " +
                    "and must contain letters and numbers.";
            logger.info(msg);
            return new Result<Null>(RespStatus.FAIL, msg);
        }

        RespStatus status;
        String msg;

        if (RoleType.Student.match(type) || RoleType.Teacher.match(type)) {
            roleService.createUser(type, username, password);
            msg = "User created";
            status = RespStatus.SUCCESS;
            logger.info(msg);
        } else {
            msg = "Role type error";
            status = RespStatus.FAIL;
            logger.warn(msg);
        }

        return new Result<Null>(status, msg);
    }
}
