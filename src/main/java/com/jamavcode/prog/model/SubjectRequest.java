package com.jamavcode.prog.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubjectRequest(
    @NotBlank
    String title,

    @NotNull
    String description
) {
}
