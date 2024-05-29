package com.jamavcode.prog.exception;

/**
 * Виняток, що викидається, коли предмет з вказаним ідентифікатором не знайдений.
 */
public class SubjectNotFoundException extends RuntimeException {

    private final int subject_id;

    /**
     * Конструктор з параметром subject_id.
     *
     * @param subject_id Ідентифікатор предмета, який не знайдений.
     */
    public SubjectNotFoundException(int subject_id) {
        this.subject_id = subject_id;
    }

    /**
     * Повертає повідомлення про помилку.
     *
     * @return Повідомлення про помилку з ідентифікатором предмета.
     */
    @Override
    public String getMessage() {
        return "Subject with id = " + subject_id + " not found";
    }
}
