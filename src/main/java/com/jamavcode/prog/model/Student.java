package com.jamavcode.prog.model;

import java.time.LocalDate;

/**
 * Представляє інформацію про студента.
 */
public record Student(
    int student_id,          // Унікальний ідентифікатор студента.
    String first_name,       // Ім'я студента.
    String last_name,        // Прізвище студента.
    LocalDate date_of_birth, // Дата народження студента.
    String group_name        // Назва групи, до якої належить студент.
) {
}
