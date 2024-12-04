package com.jamavcode.prog.service;

import com.jamavcode.prog.exception.TaskNotFoundException;
import com.jamavcode.prog.model.Task;
import com.jamavcode.prog.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void getTasksBySubjectId_ShouldReturnTasks() {
        // Arrange
        int subjectId = 1;
        List<Task> mockTasks = List.of(
            new Task(1, "Task 1", "Content 1", 100.0, subjectId),
            new Task(2, "Task 2", "Content 2", 90.0, subjectId)
        );
        when(taskRepository.getTasksBySubjectId(subjectId)).thenReturn(mockTasks);

        // Act
        List<Task> result = taskService.getTasksBySubjectId(subjectId);

        // Assert
        assertEquals(mockTasks, result);
        verify(taskRepository, times(1)).getTasksBySubjectId(subjectId);
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenTaskExists() {
        // Arrange
        int taskId = 1;
        Task mockTask = new Task(taskId, "Task 1", "Content 1", 100.0, 1);
        when(taskRepository.getTaskById(taskId)).thenReturn(Optional.of(mockTask));

        // Act
        Task result = taskService.getTaskById(taskId);

        // Assert
        assertEquals(mockTask, result);
        verify(taskRepository, times(1)).getTaskById(taskId);
    }

    @Test
    void getTaskById_ShouldThrowException_WhenTaskNotFound() {
        // Arrange
        int taskId = 1;
        when(taskRepository.getTaskById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
        assertEquals("task with id = " + taskId + " not found", exception.getMessage());
        verify(taskRepository, times(1)).getTaskById(taskId);
    }

    @Test
    void insertTask_ShouldCallRepository() {
        // Arrange
        String title = "Task 1";
        String content = "Content 1";
        double maxScore = 100.0;
        int subjectId = 1;

        // Act
        taskService.insertTask(title, content, maxScore, subjectId);

        // Assert
        verify(taskRepository, times(1)).insertTask(title, content, maxScore, subjectId);
    }

    @Test
    void updateTaskById_ShouldUpdateTask_WhenTaskExists() {
        // Arrange
        int taskId = 1;
        String newTitle = "Updated Task";
        String newContent = "Updated Content";
        double newMaxScore = 95.0;
        Task mockTask = new Task(taskId, "Old Task", "Old Content", 100.0, 1);
        when(taskRepository.getTaskById(taskId)).thenReturn(Optional.of(mockTask));

        // Act
        taskService.updateTaskById(newTitle, newContent, newMaxScore, taskId);

        // Assert
        verify(taskRepository, times(1)).updateTaskById(newTitle, newContent, newMaxScore, taskId);
    }

    @Test
    void updateTaskById_ShouldThrowException_WhenTaskNotFound() {
        // Arrange
        int taskId = 1;
        when(taskRepository.getTaskById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> taskService.updateTaskById("Title", "Content", 100.0, taskId));
        assertEquals("task with id = " + taskId  + " not found", exception.getMessage());
        verify(taskRepository, times(1)).getTaskById(taskId);
    }

    @Test
    void deleteTaskById_ShouldDeleteTask_WhenTaskExists() {
        // Arrange
        int taskId = 1;
        Task mockTask = new Task(taskId, "Task 1", "Content 1", 100.0, 1);
        when(taskRepository.getTaskById(taskId)).thenReturn(Optional.of(mockTask));

        // Act
        taskService.deleteTaskById(taskId);

        // Assert
        verify(taskRepository, times(1)).deleteTaskById(taskId);
    }

    @Test
    void deleteTaskById_ShouldThrowException_WhenTaskNotFound() {
        // Arrange
        int taskId = 1;
        when(taskRepository.getTaskById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        TaskNotFoundException exception = assertThrows(TaskNotFoundException.class, () -> taskService.deleteTaskById(taskId));
        assertEquals("task with id = " + taskId + " not found", exception.getMessage());
        verify(taskRepository, times(1)).getTaskById(taskId);
    }
}
