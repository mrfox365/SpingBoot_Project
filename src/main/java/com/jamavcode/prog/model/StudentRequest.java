package com.jamavcode.prog.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record StudentRequest(
    @NotBlank
    String first_name,

    @NotBlank
    String last_name,

    @NotNull
    @Past
    LocalDate date_of_birth,
    
    @NotBlank
    String group_name
) {

}
