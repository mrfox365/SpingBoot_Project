package com.jamavcode.prog.service;

import java.util.List;

import com.jamavcode.prog.model.Task;

/**
 * Інтерфейс для взаємодії з завданнями.
 */
public interface TaskService {

    /**
     * Повертає список завдань за ідентифікатором предмету.
     *
     * @param subject_id Ідентифікатор предмету.
     * @return Список завдань, які відносяться до заданого предмету.
     */
    List<Task> getTasksBySubjectId(int subject_id);

    /**
     * Повертає завдання за його ідентифікатором.
     *
     * @param task_id Ідентифікатор завдання.
     * @return Об'єкт завдання з вказаним ідентифікатором.
     */
    Task getTaskById(int task_id);

    /**
     * Вставляє нове завдання у базу даних.
     *
     * @param title Назва завдання.
     * @param content Зміст завдання.
     * @param max_score Максимальний бал за завдання.
     * @param subject_id Ідентифікатор предмету, до якого відноситься завдання.
     */
    void insertTask(String title, String content, double max_score, int subject_id);

    /**
     * Оновлює дані завдання за його ідентифікатором.
     *
     * @param title Нова назва завдання.
     * @param content Новий зміст завдання.
     * @param max_score Новий максимальний бал за завдання.
     * @param task_id Ідентифікатор завдання, яке потрібно оновити.
     */
    void updateTaskById(String title, String content, double max_score, int task_id);

    /**
     * Видаляє завдання за його ідентифікатором.
     *
     * @param task_id Ідентифікатор завдання, яке потрібно видалити.
     */
    void deleteTaskById(int task_id);

}
