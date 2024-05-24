package com.jamavcode.prog.service;

import java.util.List;

import com.jamavcode.prog.model.TaskResult;

public interface TaskResultService {

    List<TaskResult> getTaskResultBySubjectId(int subject_id);

    TaskResult getTaskResultById(int result_id);

    void insertTaskResult(int task_id, int student_id, double score);

    void updateTaskResultById(double score, int result_id);

    void deleteTaskResultById(int result_id);

}
