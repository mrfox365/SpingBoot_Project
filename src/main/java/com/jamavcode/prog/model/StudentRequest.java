package com.jamavcode.prog.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

/**
 * Представляє запит на створення нового студента з вказаними даними.
 */
public record StudentRequest(
    @NotBlank
    String first_name,           // Ім'я студента.

    @NotBlank
    String last_name,            // Прізвище студента.

    @NotNull
    @Past
    LocalDate date_of_birth,     // Дата народження студента (повинна бути минулою).

    @NotBlank
    String group_name            // Назва групи, до якої буде доданий студент.
) {
}
