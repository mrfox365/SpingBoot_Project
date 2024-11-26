package com.jamavcode.prog.service;

import java.time.LocalDate;
import java.util.List;

import com.jamavcode.prog.model.Student;

/**
 * Інтерфейс для взаємодії зі студентами.
 */
public interface StudentService {

    /**
     * Повертає список студентів за ідентифікатором предмету.
     *
     * @param subject_id Ідентифікатор предмету.
     * @return Список студентів, які відносяться до заданого предмету.
     */
    List<Student> getStudentBySubjectId(int subject_id);
    
    /**
     * Повертає список студентів за ідентифікатором предмету в зворотньому порядку.
     *
     * @param subject_id Ідентифікатор предмету.
     * @return Список студентів, які відносяться до заданого предмету, у зворотньому порядку.
     */
    List<Student> getStudentBySubjectIdReverse(int subject_id);

    /**
     * Повертає студента за його ідентифікатором.
     *
     * @param student_id Ідентифікатор студента.
     * @return Об'єкт студента з вказаним ідентифікатором.
     */
    Student getStudentById(int student_id);

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
     * Вставляє нового студента та прив'язує його до предмету у базу даних.
     *
     * @param first_name Ім'я студента.
     * @param last_name Прізвище студента.
     * @param date_of_birth Дата народження студента.
     * @param group_name Назва групи студента.
     * @param subject_id Ідентифікатор предмету, до якого потрібно прив'язати студента.
     */
    void insertStudentAndSubject(String first_name, String last_name, LocalDate date_of_birth, String group_name, int subject_id);

    /**
     * Прив'язує існуючого студента до предмету у базі даних.
     *
     * @param student_id Ідентифікатор студента.
     * @param subject_id Ідентифікатор предмету, до якого потрібно прив'язати студента.
     */
    void insertStudentSubject(int student_id, int subject_id);

    /**
     * Оновлює дані студента за його ідентифікатором.
     *
     * @param first_name Нове ім'я студента.
     * @param last_name Нове прізвище студента.
     * @param date_of_birth Нова дата народження студента.
     * @param group_name Нова назва групи студента.
     * @param student_id Ідентифікатор студента, якого потрібно оновити.
     */
    void updateStudentById(String first_name, String last_name, LocalDate date_of_birth, String group_name, int student_id);

    /**
     * Видаляє студента за його ідентифікатором у зазначеному предметі у базі даних.
     *
     * @param student_id Ідентифікатор студента, якого потрібно видалити.
     * @param subject_id Ідентифікатор предмету, з якого потрібно видалити студента.
     */
    void deleteStudentById(int student_id, int subject_id);

}
