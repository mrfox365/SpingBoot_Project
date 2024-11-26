package com.jamavcode.prog.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Представляє запит на оновлення інформації про завдання з вказаними даними.
 */
public record TaskRequestUpdate(
    @NotBlank
    String title,          // Нова назва завдання. Помічена як @NotBlank, що вказує на те, що це поле не може бути порожнім або містити тільки пропуски.

    @NotBlank
    String content,        // Новий вміст або опис завдання. Помічена як @NotBlank.

    @NotNull
    @Positive
    Double max_score      // Новий максимальний бал за завдання. Помічений як @NotNull, що вказує на те, що це поле не може бути null, і @Positive, що вказує на те, що значення повинно бути позитивним.
) {

}
