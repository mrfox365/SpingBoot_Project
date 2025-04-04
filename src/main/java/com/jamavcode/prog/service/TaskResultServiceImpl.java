package com.jamavcode.prog.service;

import java.util.List;
import java.util.Objects;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.jamavcode.prog.exception.TaskNotFoundException;
import com.jamavcode.prog.exception.TaskResultNotFoundException;
import com.jamavcode.prog.model.TaskResult;
import com.jamavcode.prog.repository.TaskResultRepository;

/**
 * Реалізація інтерфейсу {@link TaskResultService}.
 */
@Primary
@Service
public class TaskResultServiceImpl implements TaskResultService {

    private final TaskResultRepository taskResultRepository;

    public TaskResultServiceImpl (TaskResultRepository taskResultRepository){
        this.taskResultRepository = Objects.requireNonNull(taskResultRepository, "taskResultRepository is null");
    }

    @Override
    public List<TaskResult> getTaskResultBySubjectId(int subject_id){
        return taskResultRepository.getTaskResultBySubjectId(subject_id);
    }

    @Override
    public List<TaskResult> getTaskResultSumForStudentSubject(){
        return taskResultRepository.getTaskResultSumForStudentSubject();
    }

    @Override
    public List<TaskResult> getTaskResultAverageForTaskSubject(){
        return taskResultRepository.getTaskResultAverageForTaskSubject();
    }

    @Override
    public List<TaskResult> getTaskResultAverageForSubject(){
        return taskResultRepository.getTaskResultAverageForSubject();
    }

    @Override
    public TaskResult getTaskResultById(int result_id){
        return taskResultRepository.getTaskResultById(result_id)
            .orElseThrow(() -> new TaskNotFoundException(result_id));
    }

    @Override
    public void insertTaskResult(int task_id, int student_id, double score){
        taskResultRepository.insertTaskResult(task_id, student_id, score);
    }

    @Override
    public void updateTaskResultById(double score, int result_id){
        var taskResult = taskResultRepository.getTaskResultById(result_id)
            .orElseThrow(() -> new TaskResultNotFoundException(result_id));
        taskResultRepository.updateTaskResultById(score, taskResult.result_id());
    }

    @Override
    public void deleteTaskResultById(int result_id){
        var taskResult = taskResultRepository.getTaskResultById(result_id)
            .orElseThrow(() -> new TaskResultNotFoundException(result_id));
        taskResultRepository.deleteTaskResultById(taskResult.result_id());
    }

}
