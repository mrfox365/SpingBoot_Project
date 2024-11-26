package com.jamavcode.prog.service;

import java.util.List;

import com.jamavcode.prog.model.TaskResult;

/**
 * Інтерфейс для взаємодії з результатами завдань.
 */
public interface TaskResultService {

    /**
     * Повертає список результатів завдань за ідентифікатором предмету.
     *
     * @param subject_id Ідентифікатор предмету.
     * @return Список результатів завдань, які відносяться до заданого предмету.
     */
    List<TaskResult> getTaskResultBySubjectId(int subject_id);

    /**
     * Повертає список результатів завдань, суму балів для кожного студента за кожен предмет.
     *
     * @return Список результатів, де кожен елемент містить суму балів для кожного студента за кожен предмет.
     */
    List<TaskResult> getTaskResultSumForStudentSubject();

    /**
     * Повертає список результатів завдань, середній бал для кожного завдання за кожен предмет.
     *
     * @return Список результатів, де кожен елемент містить середній бал для кожного завдання за кожен предмет.
     */
    List<TaskResult> getTaskResultAverageForTaskSubject();

    /**
     * Повертає список результатів завдань, середній бал для кожного предмета.
     *
     * @return Список результатів, де кожен елемент містить середній бал для кожного предмета.
     */
    List<TaskResult> getTaskResultAverageForSubject();

    /**
     * Повертає результат завдання за його ідентифікатором.
     *
     * @param result_id Ідентифікатор результату завдання.
     * @return Об'єкт результату завдання з вказаним ідентифікатором.
     */
    TaskResult getTaskResultById(int result_id);

    /**
     * Вставляє новий результат завдання у базу даних.
     *
     * @param task_id Ідентифікатор завдання.
     * @param student_id Ідентифікатор студента.
     * @param score Бал за завдання.
     */
    void insertTaskResult(int task_id, int student_id, double score);

    /**
     * Оновлює дані результату завдання за його ідентифікатором.
     *
     * @param score Новий бал за завдання.
     * @param result_id Ідентифікатор результату завдання, яке потрібно оновити.
     */
    void updateTaskResultById(double score, int result_id);

    /**
     * Видаляє результат завдання за його ідентифікатором.
     *
     * @param result_id Ідентифікатор результату завдання, який потрібно видалити.
     */
    void deleteTaskResultById(int result_id);

}
