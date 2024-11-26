package com.jamavcode.prog.repository;

import com.jamavcode.prog.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * Інтерфейс для роботи з даними завдань у базі даних.
 */
public interface TaskRepository {

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
     * @return Об'єкт Optional, який містить завдання, якщо воно знайдене, або порожній Optional, якщо ні.
     */
    Optional<Task> getTaskById(int task_id);

    /**
     * Вставляє нове завдання у базу даних.
     *
     * @param title Назва завдання.
     * @param content Вміст завдання.
     * @param max_score Максимальний бал за завдання.
     * @param subject_id Ідентифікатор предмету, до якого відноситься завдання.
     */
    void insertTask(String title, String content, double max_score, int subject_id);

    /**
     * Оновлює дані завдання за його ідентифікатором.
     *
     * @param title Нова назва завдання.
     * @param content Новий вміст завдання.
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
