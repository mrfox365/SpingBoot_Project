package com.jamavcode.prog.exception;

/**
 * Виняток, що викидається, коли завдання з вказаним ідентифікатором не знайдене.
 */
public class TaskNotFoundException extends RuntimeException {

    private final int task_id;

    /**
     * Конструктор з параметром task_id.
     *
     * @param task_id Ідентифікатор завдання, яке не знайдене.
     */
    public TaskNotFoundException(int task_id) {
        this.task_id = task_id;
    }

    /**
     * Повертає повідомлення про помилку.
     *
     * @return Повідомлення про помилку з ідентифікатором завдання.
     */
    @Override
    public String getMessage() {
        return "task with id = " + task_id + " not found";
    }
}
