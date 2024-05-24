package com.jamavcode.prog.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TaskRequestInsert(
    @NotBlank
    String title,

    @NotBlank
    String content,

    @NotNull
    @Positive
    Double max_score,

    @NotNull
    @Min(1)
    Integer subject_id
) {

}
