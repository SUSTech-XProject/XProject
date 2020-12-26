package com.ooad.xproject;

import com.alibaba.fastjson.JSON;
import com.ooad.xproject.bo.CommentBO;
import com.ooad.xproject.bo.ProjSettingsBO;
import com.ooad.xproject.bo.TopicBO;
import com.ooad.xproject.entity.Student;
import com.ooad.xproject.mapper.StudentMapper;
import com.ooad.xproject.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OtherTests {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    public void testComment(){
        int roleId = 3;
        String payload = "comments";

        Student student = studentMapper.selectByRoleId(roleId);

        String pl = student.getPayload();
        List<String> commentList = new ArrayList<>();
        CommentBO commentBO = JSON.parseObject(pl, CommentBO.class);
        if(commentBO == null){
            commentBO = new CommentBO();
        }
        commentList = commentBO.getComments();
        commentList.add(payload);
        commentBO.setComments(commentList);

        pl = JSON.toJSONString(commentBO);
        student.setPayload(pl);
    }

//    private StudentMapper studentMapper;
//
//    @Test
//    public void testComment(){
//        int roleId = 3;
//        String payload = "comments";
//
//        Student student = studentMapper.selectByRoleId(roleId);
//
//        String pl = student.getPayload();
//        List<String> commentList = new ArrayList<>();
//        CommentBO commentBO = JSON.parseObject(pl, CommentBO.class);
//        if(commentBO == null){
//            commentBO = new CommentBO();
//        }
//        commentList = commentBO.getComments();
//        commentList.add(payload);
//        commentBO.setComments(commentList);
//
//        pl = JSON.toJSONString(commentBO);
//        student.setPayload(pl);
//    }

    @Test
    public void testTopic() {
        TopicBO topic = TopicBO.builder().topicName("Topic123").maxTeamSize(-1).build();
        System.out.println(topic);
        String str = JSON.toJSONString(topic);
        System.out.println(str);
        TopicBO topic2 = JSON.parseObject(str, TopicBO.class);
        System.out.println(topic2);
    }

    @Test
    public void testProjSettingsBO() {
        ProjSettingsBO ps = new ProjSettingsBO(true, 3, 4, Timestamp.valueOf("2020-12-31 23:59:59"), true);
        System.out.println(ps);
        String str = JSON.toJSONString(ps);
        System.out.println(str);
        ProjSettingsBO ps2 = JSON.parseObject(str, ProjSettingsBO.class);
        System.out.println(ps2);
    }
}
