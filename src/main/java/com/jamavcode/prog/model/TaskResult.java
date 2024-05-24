package com.jamavcode.prog.model;

public record TaskResult(
    int result_id,
    int task_id,
    int student_id,
    double score
) {
}
