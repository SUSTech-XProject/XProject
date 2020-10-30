package com.ooad.xproject.controller;

import com.ooad.xproject.entity.TestObject;
import com.ooad.xproject.service.TestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping("/select")
    public TestObject index() {
        return testService.getTestObject();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void update() {
        testService.updateNum();
    }
}