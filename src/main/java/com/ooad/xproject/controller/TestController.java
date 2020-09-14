package com.ooad.xproject.controller;

import com.ooad.xproject.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping({"", "/test"})
    public String test() {
        Integer id = testService.getTestCnt();
        return "This is a test page. now id=" + id;
    }
}