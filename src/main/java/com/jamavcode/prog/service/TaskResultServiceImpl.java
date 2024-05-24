package com.jamavcode.prog.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.jamavcode.prog.exception.TaskNotFoundExeption;
import com.jamavcode.prog.exception.TaskResultNotFoundExeption;
import com.jamavcode.prog.model.TaskResult;
import com.jamavcode.prog.repository.TaskResultRepository;

@Primary
@Service
public class TaskResultServiceImpl implements TaskResultService {

    private final TaskResultRepository taskResultRepository;

    public TaskResultServiceImpl (TaskResultRepository taskResultRepository){
        this.taskResultRepository = taskResultRepository;
    }

    @Override
    public List<TaskResult> getTaskResultBySubjectId(int subject_id){
        return taskResultRepository.getTaskResultBySubjectId(subject_id);
    }

    @Override
    public TaskResult getTaskResultById(int result_id){
        return taskResultRepository.getTaskResultById(result_id)
            .orElseThrow(() -> new TaskNotFoundExeption(result_id));
    }

    @Override
    public void insertTaskResult(int task_id, int student_id, double score){
        taskResultRepository.insertTaskResult(task_id, student_id, score);
    }

    @Override
    public void updateTaskResultById(double score, int result_id){
        var taskResult = taskResultRepository.getTaskResultById(result_id)
            .orElseThrow(() -> new TaskResultNotFoundExeption(result_id));
        taskResultRepository.updateTaskResultById(score, taskResult.result_id());
    }

    @Override
    public void deleteTaskResultById(int result_id){
        var taskResult = taskResultRepository.getTaskResultById(result_id)
            .orElseThrow(() -> new TaskResultNotFoundExeption(result_id));
        taskResultRepository.deleteTaskResultById(taskResult.result_id());
    }

}
