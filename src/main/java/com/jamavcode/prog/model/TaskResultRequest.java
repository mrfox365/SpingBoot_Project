package com.jamavcode.prog.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Представляє запит на створення нового результату виконання завдання з вказаними даними.
 */
public record TaskResultRequest(
    @NotNull
    @Min(1)
    Integer task_id,      // Унікальний ідентифікатор завдання. Помічений як @NotNull і @Min(1), що вказує на те, що значення має бути не менше 1.

    @NotNull
    @Min(1)
    Integer student_id,   // Унікальний ідентифікатор студента. Помічений як @NotNull і @Min(1).

    @NotNull
    @Min(0)
    Double score          // Оцінка за виконане завдання. Помічений як @NotNull і @Min(0), що вказує на те, що значення має бути не менше 0.
) {

}
