package com.jamavcode.prog.repository;

import com.jamavcode.prog.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> getTasksBySubjectId(int subject_id);

    Optional<Task> getTaskById(int task_id);

    void insertTask(String title, String content, double max_score, int subject_id);

    void updateTaskById(String title, String content, double max_score, int task_id);

    void deleteTaskById(int task_id);

}
