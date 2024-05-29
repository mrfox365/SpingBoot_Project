package com.jamavcode.prog.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Представляє запит на вставку нового завдання з вказаними даними.
 */
public record TaskRequestInsert(
    @NotBlank
    String title,          // Назва завдання. Помічена як @NotBlank, що вказує на те, що це поле не може бути порожнім або містити тільки пропуски.

    @NotBlank
    String content,        // Вміст або опис завдання. Помічена як @NotBlank.

    @NotNull
    @Positive
    Double max_score,      // Максимальний бал за завдання. Помічений як @NotNull, що вказує на те, що це поле не може бути null, і @Positive, що вказує на те, що значення повинно бути позитивним.

    @NotNull
    @Min(1)
    Integer subject_id     // Унікальний ідентифікатор предмету, до якого буде додано завдання. Помічений як @NotNull і @Min(1), що вказує на те, що значення має бути не менше 1.
) {

}
