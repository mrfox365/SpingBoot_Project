package com.jamavcode.prog.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jamavcode.prog.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Клас для відображення рядків результатів SQL-запитів в об'єкти Student.
 */
@Component
public class StudentMapper implements RowMapper<Student> {

    /**
     * Відображає рядок з ResultSet в об'єкт Student.
     *
     * @param rs ResultSet, який містить дані з бази даних.
     * @param rowNum Номер рядка в ResultSet.
     * @return Об'єкт Student, який відображає дані з поточного рядка ResultSet.
     * @throws SQLException Якщо виникає помилка при доступі до даних.
     */
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
            rs.getInt("student_id"), 
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getDate("date_of_birth").toLocalDate(),
            rs.getString("group_name")
        );
    }
}
