package com.jamavcode.prog.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jamavcode.prog.mapper.TaskMapper;
import com.jamavcode.prog.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private static final String SQL_GET_TASK_BY_SUBJECT_ID =
                        "SELECT * FROM task WHERE subject_id = :subject_id ORDER BY task_id;";

    private static final String SQL_GET_TASK_BY_ID =
                        "SELECT * FROM task WHERE task_id = :task_id;";

    private static final String SQL_INSERT_TASK =
                        "INSERT INTO task(title, content, max_score, subject_id) VALUES\r\n" + //
                        "    (:title, :content, :max_score, :subject_id);";

    private static final String SQL_UPDATE_TASK_BY_ID =
                        "UPDATE task SET\r\n" + //
                        "    title = :title, content = :content, max_score = :max_score\r\n" + //
                        "WHERE task_id = :task_id;";
                        
    private static final String SQL_DELETE_TASK_BY_ID =
                        "DELETE FROM task_result WHERE task_id = :task_id;\r\n" + //
                        "DELETE FROM task WHERE task_id = :task_id;";

    private final TaskMapper taskMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TaskRepositoryImpl(
        TaskMapper taskMapper,
        NamedParameterJdbcTemplate jdbcTemplate
    ){
        this.taskMapper = taskMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Task> getTasksBySubjectId(int subject_id){
        var params = new MapSqlParameterSource();
        params.addValue("subject_id", subject_id);
        return jdbcTemplate.query(
            SQL_GET_TASK_BY_SUBJECT_ID, 
            params, 
            taskMapper
            );
    }

    @Override
    public Optional<Task> getTaskById(int task_id){
        var params = new MapSqlParameterSource();
        params.addValue("task_id", task_id);
        return jdbcTemplate.query(
            SQL_GET_TASK_BY_ID, 
            params, 
            taskMapper
            ).stream().findFirst();
    }

    @Override
    public void insertTask(String title, String content, double max_score, int subject_id){
        var params = new MapSqlParameterSource();
        params.addValue("title", title);
        params.addValue("content", content);
        params.addValue("max_score", max_score);
        params.addValue("subject_id", subject_id);
        jdbcTemplate.update(SQL_INSERT_TASK, params);
    }

    @Override
    public void updateTaskById(String title, String content, double max_score, int task_id){
        var params = new MapSqlParameterSource();
        params.addValue("title", title);
        params.addValue("content", content);
        params.addValue("max_score", max_score);
        params.addValue("task_id", task_id);
        jdbcTemplate.update(SQL_UPDATE_TASK_BY_ID, params);
    }

    @Override
    public void deleteTaskById(int task_id){
        var params = new MapSqlParameterSource();
        params.addValue("task_id", task_id);
        jdbcTemplate.update(SQL_DELETE_TASK_BY_ID, params);
    }

}
