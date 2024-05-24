package com.jamavcode.prog.model;

import java.time.LocalDate;

public record Student(
    int student_id,
    String first_name,
    String last_name,
    LocalDate date_of_birth,
    String group_name
) {
}
