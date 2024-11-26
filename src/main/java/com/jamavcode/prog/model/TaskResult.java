package com.jamavcode.prog.model;

/**
 * Представляє результат виконання завдання студентом.
 */
public record TaskResult(
    int result_id,      // Унікальний ідентифікатор результату.
    int task_id,        // Унікальний ідентифікатор завдання.
    int student_id,     // Унікальний ідентифікатор студента.
    double score        // Оцінка за виконане завдання.
) {
}
