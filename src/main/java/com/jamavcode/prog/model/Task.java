package com.jamavcode.prog.model;

public record Task(    
    int task_id,
    String title,
    String content,
    double max_score,
    int subject_id
) {
}
