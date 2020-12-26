package com.ooad.xproject.service.impl;

import com.ooad.xproject.entity.TestObject;
import com.ooad.xproject.mapper.TestObjectMapper;
import com.ooad.xproject.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private final TestObjectMapper testObjectMapper;

    public TestServiceImpl(TestObjectMapper testObjectMapper) {
        this.testObjectMapper = testObjectMapper;
    }

    @Override
    public TestObject getTestObject() {
        return testObjectMapper.selectFirstOne();
    }

    @Override
    public void updateNum() {
        testObjectMapper.updateNum();
    }
}
