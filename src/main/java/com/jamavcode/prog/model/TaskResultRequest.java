package com.jamavcode.prog.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TaskResultRequest(
    @NotNull
    @Min(1)
    Integer task_id,

    @NotNull
    @Min(1)
    Integer student_id,

    @NotNull
    @Min(0)
    Double score
) {

}
