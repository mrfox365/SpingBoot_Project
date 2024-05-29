package com.jamavcode.prog.exception;

/**
 * Виняток, що викидається, коли результат завдання з вказаним ідентифікатором не знайдено.
 */
public class TaskResultNotFoundException extends RuntimeException {

    private final int taskResult_id;

    /**
     * Конструктор з параметром taskResult_id.
     *
     * @param taskResult_id Ідентифікатор результату завдання, який не знайдено.
     */
    public TaskResultNotFoundException(int taskResult_id) {
        this.taskResult_id = taskResult_id;
    }

    /**
     * Повертає повідомлення про помилку.
     *
     * @return Повідомлення про помилку з ідентифікатором результату завдання.
     */
    @Override
    public String getMessage() {
        return "taskResult with id = " + taskResult_id + " not found";
    }
}
