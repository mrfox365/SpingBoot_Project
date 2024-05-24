package com.jamavcode.prog.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TaskRequestUpdate(
    @NotBlank
    String title,

    @NotBlank
    String content,

    @NotNull
    @Positive
    Double max_score
) {

}
