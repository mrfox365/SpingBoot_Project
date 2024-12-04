package com.jamavcode.prog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jamavcode.prog.exception.TaskNotFoundException;
import com.jamavcode.prog.model.TaskResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional // Автоматично скасовує зміни в базі даних після виконання кожного тесту
class TaskResultServiceImplIntegrationTest {

    @Autowired
    private TaskResultService taskResultService;

    @Test
    void testInsertAndGetTaskResult() {
        // Вставка нового результату завдання
        int taskId = 6;
        int studentId = 3;
        double score = 95.0;

        taskResultService.insertTaskResult(taskId, studentId, score);

        // Отримання результату завдання
        List<TaskResult> results = taskResultService.getTaskResultBySubjectId(5);
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).score()).isEqualTo(score);
    }

    @Test
    void testGetTaskResultById_ValidId() {
        // Припускаємо, що результат із result_id = 1 існує в базі
        TaskResult result = taskResultService.getTaskResultById(43);
        assertThat(result).isNotNull();
        assertThat(result.result_id()).isEqualTo(43);
    }

    @Test
    void testGetTaskResultById_InvalidId() {
        // Припускаємо, що результат із result_id = 9999 не існує
        int invalidId = 9999;
        assertThrows(TaskNotFoundException.class, () -> taskResultService.getTaskResultById(invalidId));
    }

    @Test
    void testUpdateTaskResult() {
        // Вставка нового результату
        int taskId = 6;
        int studentId = 3;
        double initialScore = 85.0;
        taskResultService.insertTaskResult(taskId, studentId, initialScore);

        // Оновлення результату
        TaskResult insertedResult = taskResultService.getTaskResultBySubjectId(5).get(0);
        int resultId = insertedResult.result_id();
        double updatedScore = 90.0;

        taskResultService.updateTaskResultById(updatedScore, resultId);

        // Перевірка
        TaskResult updatedResult = taskResultService.getTaskResultById(resultId);
        assertThat(updatedResult.score()).isEqualTo(updatedScore);
    }

    @Test
    void testDeleteTaskResult() {
        // Вставка нового результату
        int taskId = 6;
        int studentId = 3;
        double score = 75.0;
        taskResultService.insertTaskResult(taskId, studentId, score);

        // Видалення результату
        TaskResult insertedResult = taskResultService.getTaskResultBySubjectId(5).get(0);
        int resultId = insertedResult.result_id();

        taskResultService.deleteTaskResultById(resultId);

        // Перевірка
        assertThrows(TaskNotFoundException.class, () -> taskResultService.getTaskResultById(resultId));
    }

    @Test
    void testGetTaskResultAverageForSubject() {
        // Додавання декількох результатів для перевірки середнього
        taskResultService.insertTaskResult(6, 3, 85.0);
        taskResultService.insertTaskResult(6, 4, 95.0);

        List<TaskResult> averages = taskResultService.getTaskResultAverageForSubject();

        assertThat(averages).isNotEmpty();
        // є цінка зі значенням 1, тому сережнім значенням буде щось між 40-50
        assertThat(averages.get(0).score()).isBetween(40.0, 50.0);
    }
}
