package com.jamavcode.prog.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jamavcode.prog.model.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubjectMapper implements RowMapper<Subject>{

    @Override
    public Subject mapRow(ResultSet rs, int rowNum) throws SQLException{
        return new Subject(
            rs.getInt("subject_id"),
            rs.getString("title"),
            rs.getString("description")
            );
    }

}