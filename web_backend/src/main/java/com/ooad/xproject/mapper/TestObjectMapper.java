package com.ooad.xproject.mapper;

import com.ooad.xproject.entity.TestObject;
import org.springframework.stereotype.Repository;

@Repository
public interface TestObjectMapper {
    // auto generate
    int insert(TestObject record);

    //auto generate
    int insertSelective(TestObject record);

    TestObject selectFirstOne();
    void updateNum();
}