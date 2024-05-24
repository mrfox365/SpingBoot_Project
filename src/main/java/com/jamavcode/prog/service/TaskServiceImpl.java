package com.jamavcode.prog.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.jamavcode.prog.exception.TaskNotFoundExeption;
import com.jamavcode.prog.model.Task;
import com.jamavcode.prog.repository.TaskRepository;

@Primary
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getTasksBySubjectId(int subject_id){
        return taskRepository.getTasksBySubjectId(subject_id);
    }

    @Override
    public Task getTaskById(int task_id){
        return taskRepository.getTaskById(task_id)
            .orElseThrow(() -> new TaskNotFoundExeption(task_id));
    }

    @Override
    public void insertTask(String title, String content, double max_score, int subject_id){
        taskRepository.insertTask(title, content, max_score, subject_id);
    }

    @Override
    public void updateTaskById(String title, String content, double max_score, int task_id){
        var task = taskRepository.getTaskById(task_id)
            .orElseThrow(() -> new TaskNotFoundExeption(task_id));
        taskRepository.updateTaskById(title, content, max_score, task.task_id());
    }

    @Override
    public void deleteTaskById(int task_id){
        var task = taskRepository.getTaskById(task_id)
            .orElseThrow(() -> new TaskNotFoundExeption(task_id));
        taskRepository.deleteTaskById(task.task_id());
    }

}
