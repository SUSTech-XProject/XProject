package com.ooad.xproject.controller;

import com.alibaba.fastjson.JSONObject;
import com.ooad.xproject.entity.TestObject;
import com.ooad.xproject.service.impl.TestServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class TestController {

    private final TestServiceImpl testService;

    public TestController(TestServiceImpl testService) {
        this.testService = testService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testPage(Map<String, Object> map) {
        TestObject obj = testService.getTestObject();
        map.put("obj_str", JSONObject.toJSON(obj).toString());
        return "test_page";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String updateNumPost() {
        testService.updateNum();        // update num when it is post
        return "test_page";
    }
}