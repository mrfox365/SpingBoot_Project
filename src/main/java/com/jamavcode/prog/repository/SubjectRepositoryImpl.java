package com.jamavcode.prog.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jamavcode.prog.mapper.SubjectMapper;
import com.jamavcode.prog.model.Subject;

import java.util.Objects;
import java.util.List;
import java.util.Optional;

/**
 * Реалізація інтерфейсу {@link SubjectRepository}.
 */
@Repository
public class SubjectRepositoryImpl implements SubjectRepository {

    private static final String SQL_GET_SUBJECT_BY_ID =
                        "SELECT * FROM subject WHERE subject_id = :subject_id";
                        
    private static final String SQL_GET_ALL_SUBJECTS =
                        "SELECT * FROM subject ORDER BY subject_id";

    private static final String SQL_INSERT_SUBJECT =
                        "INSERT INTO subject(title, description) VALUES (:title, :description);";

    private static final String SQL_UPDATE_SUBJECT_BY_ID =
                        "UPDATE subject SET\r\n" + //
                        "    title = :title, description = :description\r\n" + //
                        "WHERE subject_id = :subject_id;";  

    private static final String SQL_DELETE_SUBJECT_BY_ID =
                        "DELETE FROM student_subject WHERE subject_id = :subject_id;\r\n" + //
                        "DELETE FROM task_result WHERE task_id IN (SELECT task_id FROM task WHERE subject_id = :subject_id);\r\n" + //
                        "DELETE FROM task WHERE subject_id = :subject_id;\r\n" + //
                        "DELETE FROM subject WHERE subject_id = :subject_id;";

    private final SubjectMapper subjectMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SubjectRepositoryImpl(
        SubjectMapper subjectMapper,
        NamedParameterJdbcTemplate jdbcTemplate
    ){
        this.subjectMapper = Objects.requireNonNull(subjectMapper, "subjectMapper is null");
        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "jdbcTemplate is null");
    }

    @Override
    public List<Subject> getAllSubjects(){
        return jdbcTemplate.query(SQL_GET_ALL_SUBJECTS, subjectMapper);
    }

    @Override
    public Optional<Subject> getSubjectsById(int subject_id) {
        var params = new MapSqlParameterSource();
        params.addValue("subject_id", subject_id);
        return jdbcTemplate.query(
            SQL_GET_SUBJECT_BY_ID, 
            params, 
            subjectMapper
            ).stream().findFirst();
    }

    @Override
    public void insertSubject(String title, String description){
        var params = new MapSqlParameterSource();
        params.addValue("title", title);
        params.addValue("description", description);
        jdbcTemplate.update(SQL_INSERT_SUBJECT, params);
    }

    @Override
    public void updateSubjectById(String title, String description, int subject_id){
        var params = new MapSqlParameterSource();
        params.addValue("title", title);
        params.addValue("description", description);
        params.addValue("subject_id", subject_id);
        jdbcTemplate.update(SQL_UPDATE_SUBJECT_BY_ID, params);
    }

    @Override
    public void deleteSubjectById(int subject_id){
        var params = new MapSqlParameterSource();
        params.addValue("subject_id", subject_id);
        jdbcTemplate.update(SQL_DELETE_SUBJECT_BY_ID, params);
    }

}