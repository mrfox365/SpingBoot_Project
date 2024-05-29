package com.jamavcode.prog.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Представляє запит на створення нового предмету з вказаними даними.
 */
public record SubjectRequest(
    @NotBlank
    String title,         // Назва предмету. Помічена як @NotBlank, що вказує на те, що це поле не може бути порожнім або містити тільки пропуски.

    @NotNull
    String description    // Опис предмету. Помічена як @NotNull, що вказує на те, що це поле не може бути null.
) {
}