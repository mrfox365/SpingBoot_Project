package com.jamavcode.prog.service;

import java.util.List;

import com.jamavcode.prog.model.Task;

public interface TaskService {

    List<Task> getTasksBySubjectId(int subject_id);

    Task getTaskById(int task_id);

    void insertTask(String title, String content, double max_score, int subject_id);

    void updateTaskById(String title, String content, double max_score, int task_id);

    void deleteTaskById(int task_id);

}
