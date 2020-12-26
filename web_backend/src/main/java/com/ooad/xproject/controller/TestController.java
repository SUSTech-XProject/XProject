package com.ooad.xproject.controller;

import com.ooad.xproject.constant.RespStatus;
import com.ooad.xproject.entity.TestObject;
import com.ooad.xproject.service.MailService;
import com.ooad.xproject.service.TestService;
import com.ooad.xproject.vo.AcInfoStdUpdateVO;
import com.ooad.xproject.vo.Result;
import com.ooad.xproject.vo.RoleVO;
import org.apache.ibatis.jdbc.Null;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Objects;

@RestController
public class TestController {

    private final TestService testService;
    private final MailService mailService;
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    public TestController(TestService testService, MailService mailService) {
        this.testService = testService;
        this.mailService = mailService;
    }

    @GetMapping("/test/select")
    public TestObject index() {
        return testService.getTestObject();
    }

    @RequestMapping(value = "/test/update", method = RequestMethod.GET)
    public void update() {
        testService.updateNum();
    }

    @ResponseBody
    @RequestMapping(value = "api/test/login", method = RequestMethod.POST)
    public Result<?> login(@RequestBody RoleVO requestRoleVO) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestRoleVO.getUsername();
        username = HtmlUtils.htmlEscape(username);

        if (!Objects.equals("admin", username)
                || !Objects.equals("123456", requestRoleVO.getPassword())) {
            logger.info("Username / password not correct");
            return new Result<Null>(RespStatus.FAIL);
        } else {
            logger.info("Login successfully");
            return new Result<Null>(RespStatus.SUCCESS);
        }
    }

    @ResponseBody
    @GetMapping("api/test/mail")
    public Result<?> testSendMail(@RequestParam("to") String to,
                                  @RequestParam("subject") String subject,
                                  @RequestParam("content") String content) {
        mailService.sendSimpleMail(to, subject, content);
        return new Result<>(null);
    }

    @ResponseBody
    @PostMapping("api/test/vo-default")
    public Result<?> testVODefault(@RequestBody AcInfoStdUpdateVO acInfoStdUpdateVO) {
        System.out.println(acInfoStdUpdateVO);
        return new Result<>(true);
    }
}