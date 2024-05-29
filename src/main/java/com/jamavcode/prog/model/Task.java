package com.jamavcode.prog.model;

/**
 * Представляє інформацію про завдання.
 */
public record Task(    
    int task_id,         // Унікальний ідентифікатор завдання.
    String title,        // Назва завдання.
    String content,      // Вміст або опис завдання.
    double max_score,    // Максимальний бал за завдання.
    int subject_id       // Унікальний ідентифікатор предмету, до якого належить завдання.
) {
}
