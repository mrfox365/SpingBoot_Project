package com.jamavcode.prog.repository;

import com.jamavcode.prog.model.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Інтерфейс для роботи з даними студентів у базі даних.
 */
public interface StudentRepository {

    /**
     * Повертає список студентів за ідентифікатором предмету.
     *
     * @param subject_id Ідентифікатор предмету.
     * @return Список студентів, які вивчають заданий предмет.
     */
    List<Student> getStudentBySubjectId(int subject_id);

    /**
     * Повертає список студентів за ідентифікатором предмету у зворотному порядку.
     *
     * @param subject_id Ідентифікатор предмету.
     * @return Список студентів, які вивчають заданий предмет у зворотному порядку.
     */
    List<Student> getStudentBySubjectIdReverse(int subject_id);

    /**
     * Повертає студента за його ідентифікатором.
     *
     * @param student_id Ідентифікатор студента.
     * @return Об'єкт Optional, який містить студента, якщо він знайдений, або порожній Optional, якщо ні.
     */
    Optional<Student> getStudentById(int student_id);

    /**
     * Вставляє нового студента у базу даних.
     *
     * @param first_name Ім'я студента.
     * @param last_name Прізвище студента.
     * @param date_of_birth Дата народження студента.
     * @param group_name Назва групи студента.
     */
    void insertStudent(String first_name, String last_name, LocalDate date_of_birth, String group_name);

    /**
     * Вставляє нового студента та предмет, який він вивчає, у базу даних.
     *
     * @param first_name Ім'я студента.
     * @param last_name Прізвище студента.
     * @param date_of_birth Дата народження студента.
     * @param group_name Назва групи студента.
     * @param subject_id Ідентифікатор предмету, який вивчає студент.
     */
    void insertStudentAndSubject(String first_name, String last_name, LocalDate date_of_birth, String group_name, int subject_id);

    /**
     * Вставляє предмет для студента у базу даних.
     *
     * @param student_id Ідентифікатор студента.
     * @param subject_id Ідентифікатор предмету.
     */
    void insertStudentSubject(int student_id, int subject_id);

    /**
     * Оновлює дані студента за його ідентифікатором.
     *
     * @param first_name Ім'я студента.
     * @param last_name Прізвище студента.
     * @param date_of_birth Дата народження студента.
     * @param group_name Назва групи студента.
     * @param student_id Ідентифікатор студента.
     */
    void updateStudentById(String first_name, String last_name, LocalDate date_of_birth, String group_name, int student_id);

    /**
     * Видаляє студента за його ідентифікатором.
     *
     * @param student_id Ідентифікатор студента.
     * @param subject_id Ідентифікатор предмету.
     */
    void deleteStudentById(int student_id, int subject_id);
}
