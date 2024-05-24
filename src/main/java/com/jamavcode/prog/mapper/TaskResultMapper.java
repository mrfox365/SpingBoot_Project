package com.jamavcode.prog.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jamavcode.prog.model.TaskResult;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TaskResultMapper implements RowMapper<TaskResult> {

    @Override
    public TaskResult mapRow(ResultSet rs, int mapRow) throws SQLException{
        return new TaskResult(
            rs.getInt("result_id"), 
            rs.getInt("task_id"), 
            rs.getInt("student_id"), 
            rs.getDouble("score")
            );
    }

}
