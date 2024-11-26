package com.jamavcode.prog.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jamavcode.prog.model.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Клас для відображення рядків результатів SQL-запитів в об'єкти Subject.
 */
@Component
public class SubjectMapper implements RowMapper<Subject> {

    /**
     * Відображає рядок з ResultSet в об'єкт Subject.
     *
     * @param rs ResultSet, який містить дані з бази даних.
     * @param rowNum Номер рядка в ResultSet.
     * @return Об'єкт Subject, який відображає дані з поточного рядка ResultSet.
     * @throws SQLException Якщо виникає помилка при доступі до даних.
     */
    @Override
    public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Subject(
            rs.getInt("subject_id"), 
            rs.getString("title"),
            rs.getString("description")
        );
    }
}
