package com.ooad.xproject.controller;

import com.alibaba.fastjson.JSON;
import com.ooad.xproject.entity.Role;
import com.ooad.xproject.entity.Student;
import com.ooad.xproject.service.StudentService;
import com.ooad.xproject.utils.RoleUtils;
import com.ooad.xproject.vo.Result;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class HomePageControllerTest {
    @Autowired
    private StudentService studentService;

    @Test
    void getComments() {

        Student student = studentService.getStudentByRoleId(12);
        String str = student.getPayload();
        String jsonStr;

        if (str == null || str.equals("")) {
            jsonStr = JSON.toJSONString(null);
        } else {
            String[] comments = str.split(";");
            jsonStr = JSON.toJSONString(comments);
        }
        System.out.println(jsonStr);
    }
}