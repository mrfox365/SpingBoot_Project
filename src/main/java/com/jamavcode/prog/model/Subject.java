package com.jamavcode.prog.model;

/**
 * Представляє інформацію про предмет.
 */
public record Subject(    
    int subject_id,     // Унікальний ідентифікатор предмету.
    String title,       // Назва предмету.
    String description  // Опис предмету.
) {
}
