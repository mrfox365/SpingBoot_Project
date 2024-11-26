package com.jamavcode.prog.service;

import java.util.List;

import com.jamavcode.prog.model.Subject;

/**
 * Інтерфейс для взаємодії з предметами.
 */
public interface SubjectService {

    /**
     * Повертає список всіх предметів.
     *
     * @return Список усіх предметів.
     */
    List<Subject> getAllSubjects();

    /**
     * Повертає предмет за його ідентифікатором.
     *
     * @param subject_id Ідентифікатор предмету.
     * @return Об'єкт предмету з вказаним ідентифікатором.
     */
    Subject getSubjectById(int subject_id);

    /**
     * Вставляє новий предмет у базу даних.
     *
     * @param title Назва предмету.
     * @param description Опис предмету.
     */
    void insertSubject(String title, String description);

    /**
     * Оновлює дані предмету за його ідентифікатором.
     *
     * @param title Нова назва предмету.
     * @param description Новий опис предмету.
     * @param subject_id Ідентифікатор предмету, який потрібно оновити.
     */
    void updateSubjectById(String title, String description, int subject_id);

    /**
     * Видаляє предмет за його ідентифікатором.
     *
     * @param subject_id Ідентифікатор предмету, який потрібно видалити.
     */
    void deleteSubjectById(int subject_id);

}
