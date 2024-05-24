package com.jamavcode.prog.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jamavcode.prog.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TaskMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException{
        return new Task(
            rs.getInt("task_id"),
            rs.getString("title"),
            rs.getString("content"),
            rs.getDouble("max_score"),
            rs.getInt("subject_id")
        );
    }

}