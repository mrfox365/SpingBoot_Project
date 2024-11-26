package com.jamavcode.prog.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jamavcode.prog.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Клас для відображення рядків результатів SQL-запитів в об'єкти Task.
 */
@Component
public class TaskMapper implements RowMapper<Task> {

    /**
     * Відображає рядок з ResultSet в об'єкт Task.
     *
     * @param rs ResultSet, який містить дані з бази даних.
     * @param rowNum Номер рядка в ResultSet.
     * @return Об'єкт Task, який відображає дані з поточного рядка ResultSet.
     * @throws SQLException Якщо виникає помилка при доступі до даних.
     */
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Task(
            rs.getInt("task_id"), 
            rs.getString("title"),
            rs.getString("content"),
            rs.getDouble("max_score"),
            rs.getInt("subject_id")
        );
    }
}
