package com.jamavcode.prog.repository;

import com.jamavcode.prog.model.TaskResult;

import java.util.List;
import java.util.Optional;

public interface TaskResultRepository {

    List<TaskResult> getTaskResultBySubjectId(int subject_id);

    Optional<TaskResult> getTaskResultById(int result_id);

    void insertTaskResult(int task_id, int student_id, double score);

    void updateTaskResultById(double score, int result_id);

    void deleteTaskResultById(int result_id);

}
