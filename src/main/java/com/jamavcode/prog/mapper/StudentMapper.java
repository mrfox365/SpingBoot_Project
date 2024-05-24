package com.jamavcode.prog.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jamavcode.prog.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException{
        return new Student(
            rs.getInt("student_id"), 
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getDate("date_of_birth").toLocalDate(),
            rs.getString("group_name")
            );
    }

}