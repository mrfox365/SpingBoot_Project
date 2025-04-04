package com.jamavcode.prog.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.jamavcode.prog.mapper.StudentMapper;
import com.jamavcode.prog.model.Student;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.List;
import java.util.Optional;

/**
 * Реалізація інтерфейсу {@link StudentRepository}.
 */
@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private static final String SQL_GET_STUDENT_BY_SUBJECT_ID =
                        "SELECT s.* FROM student s\r\n" + //
                        "    JOIN student_subject ss on s.student_id = ss.student_id\r\n" + //
                        "WHERE ss.subject_id = :subject_id ORDER BY s.student_id;";

    private static final String SQL_GET_STUDENT_BY_SUBJECT_ID_REVERSE =
                        "SELECT s.*\r\n" + //
                        "FROM student s\r\n" + //
                        "    LEFT JOIN student_subject ss ON s.student_id = ss.student_id AND ss.subject_id = :subject_id\r\n" + //
                        "WHERE ss.student_id IS NULL\r\n" + //
                        "ORDER BY s.first_name;";

    private static final String SQL_GET_STUDENT_BY_ID =
                        "SELECT * FROM student WHERE student_id = :student_id";

    private static final String SQL_INSERT_STUDENT =
                        "INSERT INTO student(first_name, last_name, date_of_birth, group_name)\r\n" + //
                        "    VALUES (:first_name, :last_name, :date_of_birth, :group_name);";

    private static final String SQL_INSERT_STUDENT_SUBJECT = 
                        "INSERT INTO student_subject (student_id, subject_id) VALUES (:student_id, :subject_id)";

    private static final String SQL_UPDATE_STUDENT_BY_ID =
                        "UPDATE student SET\r\n" + //
                        "    first_name = :first_name, last_name = :last_name, date_of_birth = :date_of_birth, group_name = :group_name\r\n" + //
                        "WHERE student_id = :student_id;";  

    private static final String SQL_DELETE_STUDENT_BY_ID =
                        "CALL delete_student_with_check(:student_id, :subject_id);";

    private final StudentMapper studentMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StudentRepositoryImpl(
        StudentMapper studentMapper,
        NamedParameterJdbcTemplate jdbcTemplate
    ){
        this.studentMapper = Objects.requireNonNull(studentMapper, "studentMapper is null");
        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "jdbcTemplate is null");
    }

    @Override
    public List<Student> getStudentBySubjectId(int subject_id){
        var params = new MapSqlParameterSource();
        params.addValue("subject_id", subject_id);
        return jdbcTemplate.query(
            SQL_GET_STUDENT_BY_SUBJECT_ID, 
            params, 
            studentMapper
            );
    }

    @Override
    public List<Student> getStudentBySubjectIdReverse(int subject_id){
        var params = new MapSqlParameterSource();
        params.addValue("subject_id", subject_id);
        return jdbcTemplate.query(
            SQL_GET_STUDENT_BY_SUBJECT_ID_REVERSE, 
            params, 
            studentMapper
            );
    }

    @Override
    public Optional<Student> getStudentById(int student_id){
        var params = new MapSqlParameterSource();
        params.addValue("student_id", student_id);
        return jdbcTemplate.query(
            SQL_GET_STUDENT_BY_ID, 
            params, 
            studentMapper
            ).stream().findFirst();
    }

    @Override
    public void insertStudent(String first_name, String last_name, LocalDate date_of_birth, String group_name){
        var params = new MapSqlParameterSource();
        params.addValue("first_name", first_name);
        params.addValue("last_name", last_name);
        params.addValue("date_of_birth", date_of_birth);
        params.addValue("group_name", group_name);
        jdbcTemplate.update(SQL_INSERT_STUDENT, params);
    }

    @Override
    @Transactional
    public void insertStudentAndSubject(String first_name, String last_name, LocalDate date_of_birth, String group_name, int subject_id){
        var params = new MapSqlParameterSource();
        params.addValue("first_name", first_name);
        params.addValue("last_name", last_name);
        params.addValue("date_of_birth", date_of_birth);
        params.addValue("group_name", group_name);

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(SQL_INSERT_STUDENT, params, keyHolder, new String[]{"student_id"});

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Не вдалося отримати student_id після вставки студента");
        }
        int student_id = key.intValue();

        params = new MapSqlParameterSource();
        params.addValue("student_id", student_id);
        params.addValue("subject_id", subject_id);
        
        jdbcTemplate.update(SQL_INSERT_STUDENT_SUBJECT, params);
    }

    @Override
    public void insertStudentSubject(int student_id, int subject_id){
        var params = new MapSqlParameterSource();
        params.addValue("student_id", student_id);
        params.addValue("subject_id", subject_id);
        jdbcTemplate.update(SQL_INSERT_STUDENT_SUBJECT, params);
    }

    @Override
    public void updateStudentById(String first_name, String last_name, LocalDate date_of_birth, String group_name, int student_id){
        var params = new MapSqlParameterSource();
        params.addValue("first_name", first_name);
        params.addValue("last_name", last_name);
        params.addValue("date_of_birth", date_of_birth);
        params.addValue("group_name", group_name);
        params.addValue("student_id", student_id);
        jdbcTemplate.update(SQL_UPDATE_STUDENT_BY_ID, params);
    }

    @Override
    public void deleteStudentById(int student_id, int subject_id){
        var params = new MapSqlParameterSource();
        params.addValue("student_id", student_id);
        params.addValue("subject_id", subject_id);
        jdbcTemplate.update(SQL_DELETE_STUDENT_BY_ID, params);
    }

}
