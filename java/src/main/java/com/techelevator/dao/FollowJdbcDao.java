package com.techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

// Not even sure if we need a Follow Jdbc?
public class FollowJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    public FollowJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Don't think we need?
//    public getFollowerIdByUserId(userID) {
//
//    }

}
