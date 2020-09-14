package com.ooad.xproject.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    // use jdbc but not mybatis first.
    // it will be removed later.
    // the param of database is in recourses/application.yml
    private final JdbcTemplate jdbcTemplate;

    public TestService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getTestCnt() {

        String selectSql = "select * from test_table limit 1";
        Integer nowId = jdbcTemplate.queryForObject(selectSql, Integer.class) + 1;

        String updateSql = "update test_table set id=" + nowId;
        jdbcTemplate.execute(updateSql);

        return nowId;
    }
}
