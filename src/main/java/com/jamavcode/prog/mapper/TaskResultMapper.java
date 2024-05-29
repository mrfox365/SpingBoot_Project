package com.jamavcode.prog.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jamavcode.prog.model.TaskResult;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Клас для відображення рядків результатів SQL-запитів в об'єкти TaskResult.
 */
@Component
public class TaskResultMapper implements RowMapper<TaskResult> {

    /**
     * Відображає рядок з ResultSet в об'єкт TaskResult.
     *
     * @param rs ResultSet, який містить дані з бази даних.
     * @param rowNum Номер рядка в ResultSet.
     * @return Об'єкт TaskResult, який відображає дані з поточного рядка ResultSet.
     * @throws SQLException Якщо виникає помилка при доступі до даних.
     */
    @Override
    public TaskResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TaskResult(
            rs.getInt("result_id"), 
            rs.getInt("task_id"), 
            rs.getInt("student_id"), 
            rs.getDouble("score")
        );
    }
}
