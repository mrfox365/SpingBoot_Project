package com.jamavcode.prog.repository;

import com.jamavcode.prog.model.Subject;

import java.util.List;
import java.util.Optional;

/**
 * Інтерфейс для роботи з даними предметів у базі даних.
 */
public interface SubjectRepository {

    /**
     * Повертає список всіх предметів.
     *
     * @return Список всіх предметів у базі даних.
     */
    List<Subject> getAllSubjects();

    /**
     * Повертає предмет за його ідентифікатором.
     *
     * @param subject_id Ідентифікатор предмету.
     * @return Об'єкт Optional, який містить предмет, якщо він знайдений, або порожній Optional, якщо ні.
     */
    Optional<Subject> getSubjectsById(int subject_id);

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
