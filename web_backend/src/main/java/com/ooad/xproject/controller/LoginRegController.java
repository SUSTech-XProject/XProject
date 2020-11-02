package com.ooad.xproject.controller;

import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.service.RoleService;
import com.ooad.xproject.vo.Result;
import com.ooad.xproject.vo.UserVO;
import org.apache.ibatis.jdbc.Null;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
public class LoginRegController {

    private final RoleService roleService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    public LoginRegController(RoleService testService) {
        this.roleService = testService;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public Result<?> login(@RequestBody UserVO requestUserVO) {
        String username = requestUserVO.getUsername();
        username = HtmlUtils.htmlEscape(username);
        String password = requestUserVO.getPassword();
        password = HtmlUtils.htmlEscape(password);

        SvResult<Role> res = roleService.checkRolePwd(username, password);

        logger.info(res.getMsg());
        if (res.failed()) {
            return new Result<Null>(RespStatus.Status400, res.getMsg());
        } else {
            return new Result<Null>(RespStatus.Status200, res.getMsg());
        }
    }
}
