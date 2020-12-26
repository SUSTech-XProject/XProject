package com.ooad.xproject.service.impl;

import com.ooad.xproject.bo.RecordUnitBO;
import com.ooad.xproject.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProjectServiceImplTest {

    @Autowired
    private ProjectService projectService;

    @Test
    void getRecordUnitList() {
        List<RecordUnitBO> recordUnitBOList = projectService.getRecordUnitList(1);
        for (int i = 0; i < recordUnitBOList.size(); ++i) {
            System.out.println(recordUnitBOList.get(i).toString());
        }
    }
}