package com.jamavcode.prog.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jamavcode.prog.mapper.TaskResultMapper;
import com.jamavcode.prog.model.TaskResult;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskResultRepositoryImpl implements TaskResultRepository {

    private static final String SQL_GET_TASK_RESULT_BY_SUBJECT_ID =
                        "SELECT tr.* FROM task_result tr\r\n" + //
                        "    JOIN task t on tr.task_id = t.task_id\r\n" + //
                        "WHERE t.subject_id = :subject_id;";

    private static final String SQL_GET_TASK_RESULT_BY_ID =
                        "SELECT * FROM task_result WHERE result_id = :result_id;";

    private static final String SQL_INSERT_TASK_RESULT =
                        "INSERT INTO task_result(task_id, student_id, score) VALUES\r\n" + //
                        "    (:task_id, :student_id, :score);";

    private static final String SQL_UPDATE_TASK_RESULT_BY_ID =
                        "UPDATE task_result SET\r\n" + //
                        "    score = :score\r\n" + //
                        "WHERE result_id = :result_id;";

    private static final String SQL_DELETE_TASK_RESULT_BY_ID =
                        "DELETE FROM task_result WHERE result_id = :result_id;";

    private final TaskResultMapper taskResultMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TaskResultRepositoryImpl(
        TaskResultMapper taskResultMapper,
        NamedParameterJdbcTemplate jdbcTemplate
    ){
        this.taskResultMapper = taskResultMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TaskResult> getTaskResultBySubjectId(int subject_id){
        var params = new MapSqlParameterSource();
        params.addValue("subject_id", subject_id);
        return jdbcTemplate.query(
            SQL_GET_TASK_RESULT_BY_SUBJECT_ID, 
            params, 
            taskResultMapper
            );
    }

    @Override
    public Optional<TaskResult> getTaskResultById(int result_id){
        var params = new MapSqlParameterSource();
        params.addValue("result_id", result_id);
        return jdbcTemplate.query(
            SQL_GET_TASK_RESULT_BY_ID, 
            params, 
            taskResultMapper
            ).stream().findFirst();
    }

    @Override
    public void insertTaskResult(int task_id, int student_id, double score){
        var params = new MapSqlParameterSource();
        params.addValue("task_id", task_id);
        params.addValue("student_id", student_id);
        params.addValue("score", score);
        jdbcTemplate.update(SQL_INSERT_TASK_RESULT, params);
    }

    @Override
    public void updateTaskResultById(double score, int result_id){
        var params = new MapSqlParameterSource();
        params.addValue("score", score);
        params.addValue("result_id", result_id);
        jdbcTemplate.update(SQL_UPDATE_TASK_RESULT_BY_ID, params);
    }

    @Override
    public void deleteTaskResultById(int result_id){
        var params = new MapSqlParameterSource();
        params.addValue("result_id", result_id);
        jdbcTemplate.update(SQL_DELETE_TASK_RESULT_BY_ID, params);
    }

}
