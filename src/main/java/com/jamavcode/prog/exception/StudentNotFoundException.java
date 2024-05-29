package com.jamavcode.prog.exception;

/**
 * Виняток, що викидається, коли студент з вказаним ідентифікатором не знайдений.
 */
public class StudentNotFoundException extends RuntimeException {

    private final int student_id;

    /**
     * Конструктор з параметром student_id.
     *
     * @param student_id Ідентифікатор студента, який не знайдений.
     */
    public StudentNotFoundException(int student_id) {
        this.student_id = student_id;
    }

    /**
     * Повертає повідомлення про помилку.
     *
     * @return Повідомлення про помилку з ідентифікатором студента.
     */
    @Override
    public String getMessage() {
        return "student with id = " + student_id + " not found";
    }
}
