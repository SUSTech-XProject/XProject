package com.ooad.xproject.service.impl;

import com.ooad.xproject.entity.TestObject;
import com.ooad.xproject.service.TestService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    // use jdbc but not mybatis first.
    // it will be removed later.
    // the param of database is in recourses/application.yml
    private final JdbcTemplate jdbcTemplate;

    public TestServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TestObject getTestObject() {
        String selectSql = "select * from test_table where id = 1 limit 1";
        // lambda expression for RowMapper
        return jdbcTemplate.queryForObject(selectSql, (rs, i) -> {
            TestObject obj = new TestObject();
            obj.setId(rs.getInt("id"));
            obj.setStr(rs.getString("str"));
            obj.setNum(rs.getInt("num"));
            return obj;
        });
    }

    @Override
    public void updateNum() {
        String updateSql = "update test_table set num = num + 1 where id = 1";
        jdbcTemplate.execute(updateSql);
    }
}
